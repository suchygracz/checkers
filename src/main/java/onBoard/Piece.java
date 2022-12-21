package onBoard;

public class Piece {
    int ID;
    String color;
    String type;
    int posX, posY;
    public Piece(String color, String type, int posX, int posY) {
        this.color = color;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }
    public void movePiece(Piece piece, int desiredPosX, int desiredPosY){
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
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
