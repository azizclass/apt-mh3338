from gofish1 import getCard

def drawCardFixed(name, deck, hand):

  ''' Draw a new card from the deck and add it to the hand. If the hand now holds the rank
      in all four suits, then remove them from the hand.

      name: A string with the name of the playerHand, used on for display purposes.
      deck: A deck as described above
      hand: A hand dictionary as described above

      Returns: None.
  '''

  if len(deck) > 0: # protect against empty deck
    newCard = getCard(deck)
    cardRank = newCard[0]
    cardSuit = newCard[1]

    if cardRank in hand:
      # append this suit to the result
      hand[cardRank].append(cardSuit)
      if len(hand[cardRank]) == 4:
        print name, "lay down", cardRank + "s"
        del hand[cardRank]

    else:
      # first of this suit, create a list with one element
      hand[cardRank] =  [ cardSuit ]
