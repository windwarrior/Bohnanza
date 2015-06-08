package nl.utwente.bpsd.model;

import java.util.List;

/**
 * Describes a card exchange between two players during trading phase of the game.
 */
public interface Exchange {

    /**
     * @return true if player takes part in an exchange
     */
    public boolean isPlayerInExchange(Player p);

    /**
     * Returns the cards that the specified player offered in for exchange.
     * @require isPlayerInExchange(p)
     * @return a list of cards offered by the player.
     */
    public List<Card> getOfferedCards(Player p);

    /**
     * Returns the exchange state of the specified Player.
     * @require isPlayerInExchange(p)
     */
    public Exchange.SideState getSideState(Player p);

    /**
     * Determines if this exchange was started by both player sides.
     * @return {@code true} if both parties started the exchange; otherwise
     *         {@code false}.
     */
    public boolean isStarted();

    /**
     * Determines if this exchange was stopped by any player side.
     * @return {@code true} if any of the sides stopped the exchange; otherwise
     *         {@code false}.
     */
    public boolean isStopped();

    /**
     * Determines if this exchange was accepted by both parties.
     * @return {@code true} if both sides accepted the exchange; otherwise
     *         {@code false}.
     */
    public boolean isAccepted();

    /**
     * Determines if this exchange was declined by any party.
     * @return {@code true} if any side declined the exchange; otherwise
     *         {@code false}.
     */
    public boolean isDeclined();

    /**
     * Determines if this exchange is finished because it was declined or
     * accepted.
     * @return {@code true} if the exchange was finished; otherwise
     *         {@code false}.
     */
    public boolean isFinished();

    /**
     *Possible players states in during exchange process
     */
    public enum SideState {
        /**
         * The state is unknown; e.g., the exchange has just been proposed and
         * the second player has not yet decided to accept or decline to trade.
         */
        IDLE,
        /**
         * The party is negotiating.
         */
        NEGOTIATING,
        /**
         * The party has accepted the exchange as-is.
         */
        ACCEPTING,
        /**
         * The party has declined the exchange as-is.
         */
        DECLINING
    }
}
