const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const PUBLIC_DIR = path.join(__dirname, 'out');

// Create the public directory if it doesn't exist
if (!fs.existsSync(PUBLIC_DIR)) {
    fs.mkdirSync(PUBLIC_DIR);
}

function formatBytes(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}

const server = http.createServer((req, res) => {
    // Decode URL to handle spaces/special characters
    const decodedUrl = decodeURIComponent(req.url);

    if (decodedUrl === '/' || decodedUrl === '/index.html') {
        fs.readdir(PUBLIC_DIR, { withFileTypes: true }, (err, files) => {
            if (err) {
                res.writeHead(500, { 'Content-Type': 'text/plain' });
                res.end('Internal Server Error');
                return;
            }

            const builds = [];
            files.forEach(file => {
                if (file.isFile() && (file.name.endsWith('.apk') || file.name.endsWith('.aab'))) {
                    const filePath = path.join(PUBLIC_DIR, file.name);
                    const stats = fs.statSync(filePath);
                    
                    // Parse filename format: NotTiled_VERSION_TYPE_ITERATION.ext
                    // e.g. NotTiled_2.0.0_debug_1.apk
                    const nameWithoutExt = path.basename(file.name, path.extname(file.name));
                    const parts = nameWithoutExt.split('_');
                    
                    let version = 'Unknown';
                    let type = file.name.endsWith('.apk') ? 'APK' : 'AAB';
                    let buildType = 'debug';
                    let iteration = '1';
                    
                    if (parts.length >= 4) {
                        version = parts[1];
                        buildType = parts[2];
                        iteration = parts[3];
                    }

                    // Format modified time nicely
                    const mtimeDate = new Date(stats.mtime);
                    const formattedDate = mtimeDate.toLocaleString('en-US', {
                        month: 'short',
                        day: 'numeric',
                        year: 'numeric',
                        hour: '2-digit',
                        minute: '2-digit',
                        hour12: true
                    });

                    builds.push({
                        filename: file.name,
                        version: version,
                        type: type,
                        buildType: buildType,
                        iteration: iteration,
                        size: formatBytes(stats.size),
                        date: formattedDate,
                        mtime: stats.mtime
                    });
                }
            });

            // Sort by modified time descending (newest first)
            builds.sort((a, b) => b.mtime - a.mtime);

            const buildRows = builds.map(b => `
                <div class="card">
                    <div class="card-header">
                        <div class="card-icon ${b.type.toLowerCase()}">
                            ${b.type === 'APK' ? '📱' : '📦'}
                        </div>
                        <div class="card-info">
                            <div class="card-title">${b.filename}</div>
                            <div class="card-date">Updated: ${b.date}</div>
                        </div>
                    </div>
                    
                    <div class="card-meta">
                        <span class="badge version">v${b.version}</span>
                        <span class="badge type ${b.buildType}">${b.buildType.toUpperCase()}</span>
                        <span class="badge iteration">Build #${b.iteration}</span>
                        <span class="size">${b.size}</span>
                    </div>
                    
                    <a href="/download/${encodeURIComponent(b.filename)}" class="btn-download">Download</a>
                </div>
            `).join('');

            const html = `
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NotTiled Build Distribution</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --bg-color: #0b0f19;
            --card-bg: rgba(255, 255, 255, 0.03);
            --card-border: rgba(255, 255, 255, 0.06);
            --primary: #4f46e5;
            --primary-hover: #6366f1;
            --text-main: #f3f4f6;
            --text-muted: #9ca3af;
            --apk-color: #10b981;
            --aab-color: #3b82f6;
        }
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
            background-color: var(--bg-color);
            color: var(--text-main);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 2rem 1rem;
            background-image: 
                radial-gradient(at 0% 0%, rgba(79, 70, 229, 0.15) 0px, transparent 50%),
                radial-gradient(at 100% 100%, rgba(16, 185, 129, 0.1) 0px, transparent 50%);
        }
        .container {
            width: 100%;
            max-width: 680px;
        }
        header {
            margin-bottom: 2.5rem;
            text-align: center;
        }
        h1 {
            font-size: 2.2rem;
            font-weight: 700;
            letter-spacing: -0.025em;
            margin-bottom: 0.5rem;
            background: linear-gradient(to right, #818cf8, #34d399);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        .subtitle {
            color: var(--text-muted);
            font-size: 0.95rem;
        }
        .build-list {
            display: flex;
            flex-direction: column;
            gap: 1.25rem;
        }
        .card {
            background: var(--card-bg);
            border: 1px solid var(--card-border);
            border-radius: 16px;
            padding: 1.25rem;
            display: flex;
            flex-direction: column;
            gap: 1rem;
            backdrop-filter: blur(12px);
            transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
        }
        .card:hover {
            transform: translateY(-2px);
            border-color: rgba(255, 255, 255, 0.12);
            background: rgba(255, 255, 255, 0.05);
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3);
        }
        .card-header {
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        .card-icon {
            width: 48px;
            height: 48px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            flex-shrink: 0;
        }
        .card-icon.apk {
            background: rgba(16, 185, 129, 0.1);
            border: 1px solid rgba(16, 185, 129, 0.2);
            color: var(--apk-color);
        }
        .card-icon.aab {
            background: rgba(59, 130, 246, 0.1);
            border: 1px solid rgba(59, 130, 246, 0.2);
            color: var(--aab-color);
        }
        .card-info {
            flex-grow: 1;
            min-width: 0;
        }
        .card-title {
            font-weight: 600;
            font-size: 1.05rem;
            margin-bottom: 0.25rem;
            word-break: break-all;
            color: var(--text-main);
        }
        .card-date {
            font-size: 0.8rem;
            color: var(--text-muted);
        }
        .card-meta {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            flex-wrap: wrap;
        }
        .badge {
            font-size: 0.75rem;
            font-weight: 600;
            padding: 0.15rem 0.5rem;
            border-radius: 6px;
        }
        .badge.version {
            background: rgba(255, 255, 255, 0.08);
            color: var(--text-main);
        }
        .badge.type.debug {
            background: rgba(245, 158, 11, 0.1);
            color: #fbbf24;
            border: 1px solid rgba(245, 158, 11, 0.2);
        }
        .badge.type.release {
            background: rgba(16, 185, 129, 0.1);
            color: var(--apk-color);
            border: 1px solid rgba(16, 185, 129, 0.2);
        }
        .badge.iteration {
            background: rgba(99, 102, 241, 0.1);
            color: #a5b4fc;
            border: 1px solid rgba(99, 102, 241, 0.2);
        }
        .size {
            font-size: 0.8rem;
            color: var(--text-muted);
            margin-left: 0.25rem;
        }
        .btn-download {
            background: var(--primary);
            color: #fff;
            text-decoration: none;
            padding: 0.75rem 1rem;
            border-radius: 12px;
            font-size: 0.95rem;
            font-weight: 600;
            text-align: center;
            transition: all 0.2s;
            display: block;
            width: 100%;
        }
        .btn-download:hover {
            background: var(--primary-hover);
            transform: scale(1.01);
        }
        .no-builds {
            text-align: center;
            padding: 3rem;
            background: var(--card-bg);
            border: 1px dashed var(--card-border);
            border-radius: 16px;
            color: var(--text-muted);
        }
        .no-build-icon {
            font-size: 2.5rem;
            margin-bottom: 0.75rem;
        }
        footer {
            margin-top: auto;
            padding-top: 3rem;
            font-size: 0.8rem;
            color: var(--text-muted);
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>NotTiled Build Distribution</h1>
            <div class="subtitle">Direct downloads for local Android packages</div>
        </header>

        <div class="build-list">
            ${buildRows.length > 0 ? buildRows : `
                <div class="no-builds">
                    <div class="no-build-icon">📭</div>
                    <div>No builds found in the out directory yet.</div>
                    <div style="font-size:0.85rem; margin-top:0.4rem;">Run build_apk.sh or build_aab.sh to generate builds.</div>
                </div>
            `}
        </div>

        <footer>
            NotTiled &copy; ${new Date().getFullYear()} &bull; Built with Google DeepMind Antigravity
        </footer>
    </div>
</body>
</html>
            `;
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.end(html);
        });
    } else if (decodedUrl.startsWith('/download/')) {
        const filename = decodedUrl.substring('/download/'.length);
        // Prevent directory traversal attacks
        const sanitizedFilename = path.basename(filename);
        const filePath = path.join(PUBLIC_DIR, sanitizedFilename);

        fs.access(filePath, fs.constants.F_OK, (err) => {
            if (err) {
                res.writeHead(404, { 'Content-Type': 'text/plain' });
                res.end('File Not Found');
                return;
            }

            res.writeHead(200, {
                'Content-Type': 'application/octet-stream',
                'Content-Disposition': `attachment; filename="${sanitizedFilename}"`
            });
            fs.createReadStream(filePath).pipe(res);
        });
    } else {
        res.writeHead(404, { 'Content-Type': 'text/plain' });
        res.end('Not Found');
    }
});

server.listen(PORT, () => {
    console.log(`NotTiled build server is running at http://localhost:${PORT}`);
    console.log(`Serving builds from: ${PUBLIC_DIR}`);
});
