package fyp.ui.hath_wasi.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fyp.ui.hath_wasi.Cards.Card;
import fyp.ui.hath_wasi.Players.Player;

public class SelectingTrumpComPlayer {

    //This method will check whether the com player can bid the trump.
    public static Boolean getChances(Player computerPlayer){

        Integer definiteChances = 0, number = 0, mediumChances = 0;
        String category;

        //The arrayList will store the player card deck.
        ArrayList<Card> cardSet = computerPlayer.getCardDeck();

        //Check if the card is a definite chance or a medium chance.
        for(int i = 0; i < cardSet.size(); i++){
            category = cardSet.get(i).getCategory();
            number = cardSet.get(i).getNumber();

            if(number > 10){
                definiteChances += 1;
            }
            else if(number >= 8 && number <= 10) {
                mediumChances += 1;
            }
        }
        if(definiteChances >= 5 || (definiteChances >= 5 && mediumChances >= 2)){
            return true;
        }
        else{
            return false;
        }
    }

    //Computer Player Selecting the trump.
    public static String getTrump(Player player){
        Integer countSpades = 0, countHeart = 0, countClubs = 0, countDiamonds = 0;
        String trump = "";
        ArrayList<Card> cards = player.getCardDeck();

        //Store the number of cards of the suits separately.
        HashMap<String, Integer> chances = new HashMap<String, Integer>();
        //Store the highest number of suits and the count.
        HashMap<String, Integer> maximumSuit = new HashMap<String, Integer>();


        for(int i = 0; i < cards.size(); i++){
            String category = cards.get(i).getCategory();
            Integer number = cards.get(i).getNumber();

            if(category.toLowerCase() == "spades"){
                countSpades += 1;
            }

            else if(category.toLowerCase() == "heart"){
                countHeart += 1;
            }

            else if(category.toLowerCase() == "clubs"){
                countClubs += 1;
            }

            else if(category.toLowerCase() == "diamonds"){
                countDiamonds += 1;
            }

            //Push to the hash map chances.
            chances.put("spades", countSpades);
            chances.put("hearts", countHeart);
            chances.put("clubs", countClubs);
            chances.put("diamonds", countDiamonds);
        }

        //Get the largest suit
        Integer maximum = Collections.max(chances.values());
        //Returns the number of key values maps. (for Loop)
        for(Map.Entry<String, Integer> entry : chances.entrySet()){
            if(entry.getValue() == maximum){
                String key = entry.getKey().toLowerCase();
                Integer value = entry.getValue();

                maximumSuit.put(key, value);
            }
        }

        //if there is more than 1 suit with same number of cards.
        if(maximumSuit.size() == 1){
            for(Map.Entry<String, Integer> entry: maximumSuit.entrySet()){
                trump = entry.getKey();
            }
        }
        //else randomly select a trump
        else{
            Object[] crunchOfKeys = maximumSuit.keySet().toArray();
            Object key = crunchOfKeys[new Random().nextInt(crunchOfKeys.length)];
            trump = key.toString();
        }
        return trump;
    }
}

