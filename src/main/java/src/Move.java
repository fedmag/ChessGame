package src;

import Pieces.Piece;
import java.util.UUID;

public class Move {
    private final String author;
    private final String moveID;
    private final Piece movingPiece;
    private final Piece destPiece;
    private final String fromCell;
    private final String toCell;

    public Move(Player p, int x, int y, int destX, int destY, Board board) {
        this.author = p.getName();
        this.moveID = UUID.randomUUID().toString();
        this.movingPiece = board.pieceAtDest(x, y);
        this.fromCell = "" + x + ", " + y;
        this.destPiece = board.pieceAtDest(destX, destY);
        this.toCell = "" + destX + ", " + destY;
    }

    public String toString() {
        String destName = "empty cell";
        String startName = movingPiece.getPieceName();
        if(this.destPiece != null) {
            destName = this.destPiece.getPieceName();
            destName = this.destPiece.getWhite() ?  destName.concat("white") : destName.concat("black");
        }
        startName = this.movingPiece.getWhite() ? startName.concat("white") : startName.concat("black");
        return "player: " + this.author + ", moveID: " + this.moveID + "\n from: " + this.fromCell + " to: "+ this.toCell +
                "\n piece: " + startName + " -> " + destName;
    }
}
