package Integration;

import Common.Action;
import Common.Coordinate;
import Common.State;
import Enumerations.ResponseStatus;
import Players.AIPlayer;
import Players.IStrategy;

import java.awt.*;
import java.util.Optional;

public class BrokenPlayer extends AIPlayer {

    private final BadBehavior bad;
    private final int count;
    private final boolean loop;
    private int current;

    public BrokenPlayer(IStrategy strategy, String name, String bad, int count, boolean loop) {
        super(strategy, name, Color.RED); // todo
        this.bad = BadBehavior.valueOf(bad);
        this.count = count;
        this.current = 0;
        this.loop = loop;
    }

    @Override
    public ResponseStatus setup(Optional<State> ps, Coordinate destination) {
        if (this.bad == BadBehavior.setUp) {
            this.current++;
            if (this.current == this.count) {
                this.errorOut();
            }
        }
        return super.setup(ps, destination);
    }

    @Override
    public Action takeTurn(State state) {
        if (this.bad == BadBehavior.takeTurn) {
            this.current++;
            if (this.current == this.count) {
                this.errorOut();
            }
        }
        return super.takeTurn(state);
    }

    @Override
    public ResponseStatus won(Boolean w) {
        if (this.bad == BadBehavior.win) {
            this.current++;
            if (this.current == this.count) {
                this.errorOut();
            }
        }
        return super.won(w);
    }

    private void errorOut() {
        if (this.loop) {
            while (true) {
            }
        } else {
            int x = 1/0;
        }
    }


    public enum BadBehavior {
        setUp, takeTurn, win, none
    }

}
