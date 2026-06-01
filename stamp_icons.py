#!/usr/bin/env python3
import os
import re
import shutil
from PIL import Image, ImageDraw, ImageFont

# Root directory of the project
PROJECT_ROOT = os.path.dirname(os.path.abspath(__file__))
ANDROID_DIR = os.path.join(PROJECT_ROOT, "android")
RES_DIR = os.path.join(ANDROID_DIR, "res")

# Try to find a system font
FONT_PATHS = [
    "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf",
    "/usr/share/fonts/truetype/freefont/FreeSansBold.ttf",
    "/usr/share/fonts/truetype/liberation/LiberationSans-Bold.ttf",
]
FONT_PATH = None
for path in FONT_PATHS:
    if os.path.exists(path):
        FONT_PATH = path
        break

def get_version_name():
    gradle_path = os.path.join(ANDROID_DIR, "build.gradle")
    if not os.path.exists(gradle_path):
        return "2.0.0"
    with open(gradle_path, "r", encoding="utf-8") as f:
        content = f.read()
    match = re.search(r'versionName\s+"([^"]+)"', content)
    if match:
        return match.group(1)
    return "2.0.0"

def stamp_icon(src_path, dest_path, top_text, bottom_text):
    os.makedirs(os.path.dirname(dest_path), exist_ok=True)
    with Image.open(src_path) as img:
        # Convert to RGBA to support transparency
        img = img.convert("RGBA")
        width, height = img.size
        
        # Calculate dynamic font size and stroke width based on icon size
        font_size = max(6, int(width * 0.11))
        stroke_width = max(1, int(width * 0.015))
        
        # Load font
        try:
            if FONT_PATH:
                font = ImageFont.truetype(FONT_PATH, font_size)
            else:
                font = ImageFont.load_default()
        except Exception:
            font = ImageFont.load_default()
            
        draw = ImageDraw.Draw(img)
        
        # Draw top text (Version)
        if top_text:
            # Position at center horizontal, 28% from top to avoid clipping
            pos_top = (width // 2, int(height * 0.28))
            draw.text(
                pos_top, 
                top_text, 
                fill="white", 
                font=font, 
                anchor="mm", 
                stroke_width=stroke_width, 
                stroke_fill="black"
            )
            
        # Draw bottom text (Status)
        if bottom_text:
            # Position at center horizontal, 72% from top to avoid clipping
            pos_bottom = (width // 2, int(height * 0.72))
            draw.text(
                pos_bottom, 
                bottom_text, 
                fill="white", 
                font=font, 
                anchor="mm", 
                stroke_width=stroke_width, 
                stroke_fill="black"
            )
            
        img.save(dest_path, "PNG")

def main():
    version = get_version_name()
    print(f"Detected versionName: {version}")
    
    # Define combinations
    # playStoreDebug, playStoreApkRelease, standaloneDebug, standaloneRelease, standaloneApkRelease
    variants = [
        # (flavor, build_type, top_label, bottom_label)
        ("playStore", "debug", "debug", version),
        ("playStore", "apkRelease", "", version),
        ("standalone", "debug", "debug", f"{version}-S"),
        ("standalone", "release", "", f"{version}-S"),
        ("standalone", "apkRelease", "", f"{version}-S"),
    ]
    
    # Original resource directories with mipmaps
    mipmap_dirs = [d for d in os.listdir(RES_DIR) if d.startswith("mipmap-") and d != "mipmap-anydpi-v26"]
    
    for flavor, build_type, top_label, bottom_label in variants:
        variant_name = f"{flavor}{build_type[0].upper()}{build_type[1:]}"
        print(f"Generating icons for variant: {variant_name} ({top_label} / {bottom_label})")
        
        # Clean up old source directory under src if it exists
        old_src_dir = os.path.join(ANDROID_DIR, "src", variant_name)
        if os.path.exists(old_src_dir):
            print(f"Cleaning up legacy directory: {old_src_dir}")
            shutil.rmtree(old_src_dir)
            
        variant_res_dir = os.path.join(ANDROID_DIR, "variants", variant_name, "res")
        
        # Clean existing variant res dir to ensure a fresh copy
        if os.path.exists(variant_res_dir):
            shutil.rmtree(variant_res_dir)
            
        # Copy anydpi XML files since they shouldn't be stamped but need to be present
        anydpi_src = os.path.join(RES_DIR, "mipmap-anydpi-v26")
        anydpi_dest = os.path.join(variant_res_dir, "mipmap-anydpi-v26")
        if os.path.exists(anydpi_src):
            shutil.copytree(anydpi_src, anydpi_dest)
            
        # Stamp the rest of the mipmaps
        for mipmap in mipmap_dirs:
            src_mipmap_dir = os.path.join(RES_DIR, mipmap)
            dest_mipmap_dir = os.path.join(variant_res_dir, mipmap)
            
            for file_name in os.listdir(src_mipmap_dir):
                if file_name.endswith(".png"):
                    src_file = os.path.join(src_mipmap_dir, file_name)
                    dest_file = os.path.join(dest_mipmap_dir, file_name)
                    stamp_icon(src_file, dest_file, top_label, bottom_label)
                    
    print("Done generating stamped icons!")

if __name__ == "__main__":
    main()
