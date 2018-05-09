import java.util.*

class Deck {

    // immutable cards array
    val cards: Array<Card> = Array(52, { Card(it % 13, getSuit(it)) })
    var cardsInDeck: MutableList<Card> = cards.toMutableList()

    // draw a card from the top of the deck
    fun drawCard(): Card = cardsInDeck.removeAt(0)

    // reset the deck for a new game
    fun reset() {
        cardsInDeck = cards.toMutableList()
        cardsInDeck.forEach { it.faceUp = false}
        cardsInDeck.shuffle()
    }

    private fun getSuit(i: Int): String = when (i/13) {
        0 -> clubs
        1 -> diamonds
        2 -> hearts
        else -> spades
    }
}