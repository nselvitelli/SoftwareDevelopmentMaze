package Referee;

import java.awt.Color;
import java.util.*;

/**
 * A class used to create random colors. Each color generated from a single ColorGenerator will be distinct
 * from all previously produced colors.
 */
public class ColorGenerator {

    private final Queue<Color> colors;
    private final List<Color> used;

    public ColorGenerator() {
        this.colors = new LinkedList<>();
        this.colors.add(Color.ORANGE);
        this.colors.add(Color.PINK);
        this.colors.add(Color.RED);
        this.colors.add(Color.BLUE);
        this.colors.add(Color.GREEN);
        this.colors.add(Color.YELLOW);
        this.colors.add(Color.WHITE);
        this.colors.add(Color.BLACK);
        this.used = new ArrayList<>();
    }

    /**
     * Returns the next standard color, or a random unique color if standard colors have been depleted.
     */
    public Color nextColor() {
        Color color;
        if (!this.colors.isEmpty()) {
            color = this.colors.poll();
        } else {
            Random r = new Random();
            do {
                color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            } while (this.used.contains(color));
        }
        this.used.add(color);
        return color;
    }
}
