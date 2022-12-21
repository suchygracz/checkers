package onBoard;

public class Piece {
    private int ID;
    static enum color{white, black};
    private Piece.color color;
    private int posX, posY;
    public Piece(Piece.color color, int posX, int posY) {
        this.color = color;
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
    public Piece.color getColor() {
        return color;
    }
    public void setColor(Piece.color color) {
        this.color = color;
    }
}
