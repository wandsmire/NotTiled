#!/usr/bin/env python3
"""Generate swatch.png — rainbow top-to-bottom, dark→color→washout left-to-right."""

import colorsys
from pathlib import Path

try:
    from PIL import Image
except ImportError:
    Image = None

# How far each row blends toward black (left) / white (right). 1.0 = full black/white.
DARK_BLEND = 0.35
LIGHT_BLEND = 0.65

BLACK = (0, 0, 0)
WHITE = (255, 255, 255)


def hsv_pixel(h: float, s: float, v: float) -> tuple[int, int, int]:
    r, g, b = colorsys.hsv_to_rgb(h, s, v)
    return round(r * 255), round(g * 255), round(b * 255)


def mix(
    a: tuple[int, int, int], b: tuple[int, int, int], t: float
) -> tuple[int, int, int]:
    return tuple(round(a[i] + (b[i] - a[i]) * t) for i in range(3))


def build_swatch(width: int = 17, hue_rows: int = 16) -> list[tuple[int, int, int]]:
    pixels: list[tuple[int, int, int]] = []
    center = (width - 1) / 2

    for row in range(hue_rows):
        pure = hsv_pixel(row / hue_rows, 1.0, 1.0)

        for col in range(width):
            if col <= center:
                t = col / center if center > 0 else 1.0
                t = DARK_BLEND + (1.0 - DARK_BLEND) * t
                pixels.append(mix(BLACK, pure, t))
            else:
                t = (col - center) / center if center > 0 else 1.0
                t = LIGHT_BLEND * t
                pixels.append(mix(pure, WHITE, t))

    for col in range(width):
        gray = round(col * 255 / (width - 1))
        pixels.append((gray, gray, gray))

    return pixels


def main() -> None:
    width, hue_rows = 17, 16
    height = hue_rows + 1
    out = Path(__file__).resolve().parent.parent / "swatch.png"
    pixels = build_swatch(width, hue_rows)

    if Image is not None:
        img = Image.new("RGB", (width, height))
        img.putdata(pixels)
        img.save(out)
    else:
        import struct
        import zlib

        def chunk(tag: bytes, data: bytes) -> bytes:
            return (
                struct.pack(">I", len(data))
                + tag
                + data
                + struct.pack(">I", zlib.crc32(tag + data) & 0xFFFFFFFF)
            )

        raw = b"".join(
            b"\x00" + bytes(c for px in pixels[y * width : (y + 1) * width] for c in px)
            for y in range(height)
        )
        png = (
            b"\x89PNG\r\n\x1a\n"
            + chunk(b"IHDR", struct.pack(">IIBBBBB", width, height, 8, 2, 0, 0, 0))
            + chunk(b"IDAT", zlib.compress(raw, 9))
            + chunk(b"IEND", b"")
        )
        out.write_bytes(png)

    print(f"Wrote {out} ({width}x{height}, {len(pixels)} colors)")
    print("Rows 0-15: rainbow, dark→full→washout (no pure black/white on sides)")
    print("Row 16:    grayscale black → white")


if __name__ == "__main__":
    main()
