package fyp.ui.hath_wasi;

public class Card implements Comparable<Card> {

    private int cardId;
    private boolean trump;
    private String category;
    private int imageSource;

    public boolean isTrump() {
        return trump;
    }

    public void setTrump(boolean trump) {
        this.trump = trump;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public Card (int cardId, boolean trump, String category, int imageSource){
        this.cardId =cardId;
        this.trump = trump;
        this.category = category;
        this.imageSource = imageSource;
    }


    public int compareTo(Card card){
        if(this.cardId > card.getCardId()){
            return 1;
        }

        else if(this
        .cardId < card.getCardId()){
            return -1;
        }

        else{
            return 0;
        }
    }

}
