public class piece {
    int ID;
    String kolor;
    String rodzaj;
    int posX, posY;
    public piece(String kolor, String rodzaj, int posX, int posY) {
        this.kolor = kolor;
        this.rodzaj = rodzaj;
        this.posX = posX;
        this.posY = posY;
    }
    public void movePiece(piece piece, int desiredPosX, int desiredPosY){
        piece.setPosX(desiredPosX);
        piece.setPosY(desiredPosY);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getKolor() {
        return kolor;
    }

    public void setKolor(String kolor) {
        this.kolor = kolor;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }
}
