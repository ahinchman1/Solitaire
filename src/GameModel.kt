// singleton instance
object GameModel {

    val deck = Deck()
    val wastePile: MutableList<Card> = mutableListOf()
    val foundationPiles = arrayOf(FoundationPile(clubs),
            FoundationPile(diamonds), FoundationPile(hearts),
            FoundationPile(spades))
    val tabluePiles = Array(7, { TableauPile()})

    fun resetGame() {
        wastePile.clear()
        foundationPiles.forEach { it.reset() }
        deck.reset()

        // set up each of the tablue piles where the first pile has 1 card,
        // the second pile has 2 piles, and so on until 7
        tabluePiles.forEachIndexed { i, _ ->
            val cardsInPile: MutableList<Card> = Array(i + 1, { deck.drawCard() }).toMutableList()
            tabluePiles[i] = TableauPile(cardsInPile)
        }
    }

    fun onDeckTap() {
        if (deck.cardsInDeck.size > 0 ) {
            val card = deck.drawCard()
            card.faceUp = true
            wastePile.add(card)
        } else {
            deck.cardsInDeck = wastePile.toMutableList() // returns a copy of the waste pile
            wastePile.clear()
        }
    }

    fun onWasteTap() {
        if (wastePile.size > 0) {
           val card = wastePile.last()
            if (playCard(card)) {
                wastePile.remove(card)
            }
        }
    }

    fun onFoundationTap(foundationIndex: Int) {
        val foundationPile = foundationPiles[foundationIndex]
        if (foundationPile.cards.size > 0) {
            val card = foundationPile.cards.last()
            if (playCard(card)) {
                foundationPile.removeCard(card)
            }
        }
    }

    fun onTableauTap(tableauIndex: Int, cardIndex: Int) {
        val tableauPile = tabluePiles[tableauIndex]
        if(tableauPile.cards.size > 0) {
            val cards = tableauPile.cards.subList(cardIndex, tableauPile.cards.lastIndex + 1)
            if (playCards(cards)) {
                tableauPile.removeCards(cardIndex)
            }
        }
    }

    private fun playCards(cards: MutableList<Card>): Boolean {
        if (cards.size == 1) {
            return playCard(cards.first())
        } else {
            tabluePiles.forEach {
                if (it.addCards(cards)) {
                    return true
                }
            }
        }
        return false
    }

    private fun playCard(card: Card): Boolean {
        foundationPiles.forEach {
            if (it.addCard(card)) {
                return true
            }
        }
        tabluePiles.forEach {
            if (it.addCards(mutableListOf(card))) {
                return true
            }
        }
        return false
    }

    fun debugPrint() {
        var firstLine = if (wastePile.size > 0) "${wastePile.last()}" else "___"
        firstLine = firstLine.padEnd(18)
        foundationPiles.forEach {
            firstLine += if (it.cards.size > 0 ) "${it.cards.last()}" else "___"
            firstLine += "   "
        }
        println(firstLine)
        println()
        for (i in 0..12) {
            var row = ""
            tabluePiles.forEach {
                row += if (it.cards.size > i) "${it.cards[i]}" else "   "
                row += "   "
            }
            println(row)
        }
    }

}





