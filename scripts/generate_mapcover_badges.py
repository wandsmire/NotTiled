#!/usr/bin/env python3
"""Build mapcover_badges.svg and export mapcover_badges.png via Inkscape."""

from __future__ import annotations

import subprocess
import sys
import xml.etree.ElementTree as ET
from pathlib import Path
from xml.etree.ElementTree import Element, SubElement, tostring

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / "android" / "assets" / "images"
SVG_PATH = ASSETS / "mapcover_badges.svg"
PNG_PATH = ASSETS / "mapcover_badges.png"

CELL = 56
COLS = 10
ROWS = 10
W = COLS * CELL
H = ROWS * CELL

TEAM_COLORS = [
    "#01ff01",  # 1 green
    "#f70809",  # 2 red
    "#2d32aa",  # 3 blue
    "#ffff04",  # 4 yellow
    "#04feff",  # 5 cyan
    "#e9e9ea",  # 6 white
    "#010201",  # 7 black
    "#ff01ff",  # 8 pink
    "#ff8b14",  # 9 orange
    "#a36edd",  # 10 purple
]
POOL_YELLOW = "#ffff04"

SVG_NS = "http://www.w3.org/2000/svg"
INKSCAPE_NS = "http://www.inkscape.org/namespaces/inkscape"
ET.register_namespace("", SVG_NS)
ET.register_namespace("inkscape", INKSCAPE_NS)


def badge_label(index: int) -> tuple[str, str]:
    if index == 99:
        return "$", POOL_YELLOW
    return str(index + 1), TEAM_COLORS[index % 10]


def font_size(label: str) -> float:
    if label == "$":
        return 30
    if len(label) == 1:
        return 31
    if len(label) == 2:
        return 25
    return 19


def text_y_offset(label: str) -> float:
    if label == "$":
        return 0.0
    if len(label) == 1:
        return 3.2
    if len(label) == 2:
        return 2.8
    return 2.4


def build_svg() -> bytes:
    svg = Element(
        f"{{{SVG_NS}}}svg",
        {
            "width": str(W),
            "height": str(H),
            "viewBox": f"0 0 {W} {H}",
            "version": "1.1",
        },
    )

    defs = SubElement(svg, f"{{{SVG_NS}}}defs")
    style = SubElement(defs, f"{{{SVG_NS}}}style")
    style.text = """
      .badge-text {
        font-family: 'DejaVu Sans', 'Liberation Sans', 'Arial', sans-serif;
        font-weight: 700;
        text-anchor: middle;
        dominant-baseline: central;
        paint-order: stroke fill;
        stroke: #ffffff;
        stroke-width: 3.2px;
        stroke-linejoin: round;
        stroke-linecap: round;
      }
      .badge-text-pool {
        font-family: 'DejaVu Sans Condensed', 'Liberation Sans Narrow', 'Arial Narrow', 'DejaVu Sans', sans-serif;
        font-weight: 400;
        font-stretch: condensed;
        text-anchor: middle;
        dominant-baseline: central;
        stroke: none;
      }
    """

    for index in range(COLS * ROWS):
        col = index % COLS
        row = index // COLS
        cx = col * CELL + CELL / 2
        cy = row * CELL + CELL / 2
        label, color = badge_label(index)
        is_pool = label == "$"
        text_y = cy + text_y_offset(label)

        g = SubElement(svg, f"{{{SVG_NS}}}g")

        SubElement(
            g,
            f"{{{SVG_NS}}}circle",
            {
                "cx": f"{cx:.2f}",
                "cy": f"{cy:.2f}",
                "r": "23.5",
                "fill": "#000000",
                "fill-opacity": "0.58",
            },
        )

        text = SubElement(
            g,
            f"{{{SVG_NS}}}text",
            {
                "class": "badge-text-pool" if is_pool else "badge-text",
                "x": f"{cx:.2f}",
                "y": f"{text_y:.2f}",
                "fill": color,
                "font-size": str(font_size(label)),
            },
        )
        text.text = label

    return tostring(svg, encoding="utf-8", xml_declaration=True)


def export_png() -> None:
    last: Exception | None = None
    for cmd in (
        [
            "inkscape",
            str(SVG_PATH),
            "--export-type=png",
            f"--export-filename={PNG_PATH}",
            f"--export-width={W}",
            f"--export-height={H}",
            "--export-background-opacity=0",
        ],
        [
            "inkscape",
            str(SVG_PATH),
            f"--export-png={PNG_PATH}",
            f"--export-width={W}",
            f"--export-height={H}",
            "--export-background-opacity=0",
        ],
    ):
        try:
            subprocess.run(cmd, check=True, capture_output=True, text=True)
            return
        except (subprocess.CalledProcessError, FileNotFoundError) as err:
            last = err
    raise RuntimeError(f"Inkscape export failed: {last}")


def main() -> None:
    ASSETS.mkdir(parents=True, exist_ok=True)
    SVG_PATH.write_bytes(build_svg())
    export_png()
    print(f"Wrote {SVG_PATH}")
    print(f"Wrote {PNG_PATH} ({W}x{H}, {CELL}px cells)")


if __name__ == "__main__":
    try:
        main()
    except Exception as exc:
        print(exc, file=sys.stderr)
        sys.exit(1)
