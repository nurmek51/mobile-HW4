# Art Space low-fidelity prototype

Audience: visitors browsing a small collection of original digital landscapes.

Artwork sequence: Golden Horizon (2026), Moonlit Peaks (2026), Quiet Valley (2026).
Each artwork needs its own image, title, artist, and year.

```text
+----------------------------------+
|                                  |
|        framed artwork            |
|                                  |
+----------------------------------+
| Artwork title                    |
| Artist (year)                    |
+----------------------------------+
| [ Previous ]          [ Next ]    |
+----------------------------------+
```

The artwork wall, descriptor, and display controller are separate composables.
Previous and Next move through the collection and wrap at either end.
