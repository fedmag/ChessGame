package chess.src;

import chess.pieces.Piece;
import java.util.UUID;

/**
 * Class used to identify a single move, for future DB implementation
 */
public class Move {
    private final String author;
    private final String moveID;
    private final Piece movingPiece;
    private final Piece destPiece;
    private final Cell startCell;
    private final Cell landCell;


    /**
     * Constructor
     * @param p player who performed the move
     * @param x coord x of the starting cell
     * @param y coord y of the starting cell
     * @param destX coord x of the landing cell
     * @param destY coord y of the landing cell
     * @param board current status of the board
     */
    public Move(Player p, int x, int y, int destX, int destY, Board board) {
        this.author = p.getName();
        this.moveID = UUID.randomUUID().toString();
        this.movingPiece = board.pieceAtDest(x, y);
        this.destPiece = board.pieceAtDest(destX, destY);
        this.startCell = board.getCell(x,y);
        this.landCell = board.getCell(destX, destY);
    }

    /**
     * Summary of the move
     * @return summary of the move
     */
    public String toString() {
        String destName = "empty cell";
        String startName = movingPiece.getPieceName();
        if(this.destPiece != null) {
            destName = this.destPiece.getPieceName();
            destName = this.destPiece.getWhite() ?  destName.concat(" white") : destName.concat(" black");
        }
        startName = this.movingPiece.getWhite() ? startName.concat(" white") : startName.concat(" black");
        return "player: " + this.author + ", moveID: " + this.moveID + "\n " + this.startCell.standardNameCell() + " -> "+ this.landCell.standardNameCell() +
                "\n piece: " + startName + " -> " + destName;
    }

    /**
     * concise move summary "(startingCell) -> (landingCell)
     * @return concise move summary "(startingCell) -> (landingCell)
     */
    public String cellMove() {
        return this.startCell.standardNameCell() + " -> "+ this.landCell.standardNameCell();
    }
}
