package chess.AI;

import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Rook;
import chess.src.Board;
import chess.src.Cell;
import chess.src.GameFlow;
import chess.src.Player;

public class FEN {

    public FEN() {}

    public static String  translateToFEN(Board board, Player p1, Player p2) {
        String finalFENString;
        finalFENString = gridStatus(board, p1, p2);
        finalFENString = castlingStatus(finalFENString, board, p1, p2);
        finalFENString = enPassantStatus(finalFENString, board, p1, p2);
        finalFENString = turnCounterStatus(finalFENString, board, p1, p2);
//        System.out.println(finalFENString.toString());
        return finalFENString.toString();
    }

    private static String gridStatus(Board board, Player p1, Player p2) {
        StringBuilder fenString = new StringBuilder();
        int counter = 0;
        for (int i = 7; i >= 0 ; i--) { // moving bottom to top
            for (int j = 0; j <= 7; j++) { // moving from left to right
                Piece piece = board.pieceAtDest(i,j);
                if (piece!=null) {
                    if (counter!=0) {
                        fenString.append(counter);
                        counter=0;
                    }
                    if (piece.getPieceName().equals("knight")) { // for some reason the knight is N in the FEN notation
                        if (piece.getWhite()) fenString.append(("n"));
                        else fenString.append("N");
                    } else {
                        if (piece.getWhite()) fenString.append((piece.getPieceName().substring(0, 1)));
                        else fenString.append(piece.getPieceName().substring(0, 1).toUpperCase());
                        counter = 0;
                    }
                } else {
                    counter ++;
                    if (counter==8) {
                        fenString.append(counter);
                        counter = 0;
                    }
                }
            }
            if (counter!=0) {
                fenString.append(counter);
                counter = 0;
            }
            fenString.append("/");
        }
        fenString.append(" "+ GameFlow.whoseTurnColor(p1, p2));
        return fenString.toString();
    }

    private static String castlingStatus(String fenString, Board board, Player p1, Player p2) {
        StringBuilder workingFENString = new StringBuilder(fenString);
        StringBuilder initialCastlingString = new StringBuilder();
        Piece whiteLeftRook = board.pieceAtDest(7,0);
        Piece whiteRightRook = board.pieceAtDest(7,7);
        Piece whiteKing = board.pieceAtDest(7,4);

        if (whiteKing instanceof King && whiteKing.getWhite() && !((King) whiteKing).isCastlingDone()) { // castling not performed on the king
            if (!((Rook) whiteRightRook).isCastlingDone()) initialCastlingString.append("K");
            if (!((Rook) whiteLeftRook).isCastlingDone()) initialCastlingString.append("Q");
        }
        Piece blackLeftRook = board.pieceAtDest(0,0);
        Piece blackRightRook = board.pieceAtDest(0,7);
        Piece blackKing = board.pieceAtDest(0,4);
        if (blackKing instanceof King && !blackKing.getWhite() && !((King) blackKing).isCastlingDone()) { // castling not performed on the king
            if (!((Rook) blackRightRook).isCastlingDone()) initialCastlingString.append("k");
            if (!((Rook) blackLeftRook).isCastlingDone()) initialCastlingString.append("q");
        }
        if (initialCastlingString.toString().equals("")) initialCastlingString.append("-");
        return workingFENString.append(" "+ initialCastlingString).toString();
    }

    private static String enPassantStatus(String finalFENString, Board board, Player p1, Player p2) {
        StringBuilder workingFENString = new StringBuilder(finalFENString);
        String[] cells = GameFlow.getLastMoveCells().split(" -> ");
        String fromCell = cells[0];
        String toCell = cells[1];
        // getting the last piece moved
        int x = 8 - Integer.parseInt(""+toCell.charAt(1)); // getting the x position in the matrix
        int y = Cell.letterToNumb(toCell.charAt(0)); // getting y position in the matrix
        Piece pieceJustMoved = board.pieceAtDest(x,y);
        if (pieceJustMoved instanceof Pawn) {
            if (fromCell.charAt(0) == toCell.charAt(0)) {
                x = Integer.parseInt(String.valueOf(fromCell.charAt(1)));
                y = Integer.parseInt(String.valueOf(toCell.charAt(1)));
                if (Math.abs(x-y) == 2) {
                    if (pieceJustMoved.getWhite()) {
                        return workingFENString.append(" "+Cell.yInLetter(pieceJustMoved.getyPos()) +""+(8 - pieceJustMoved.getxPos()-1)).toString();
                    }
                    else return workingFENString.append(" "+Cell.yInLetter(pieceJustMoved.getyPos()) +""+ (8 - pieceJustMoved.getxPos()+1)).toString();
                }
            }
        }
        return workingFENString.append(" -").toString();
    }

    private static String turnCounterStatus(String finalFENString, Board board, Player p1, Player p2) {
        // for semplicity I am not considering the fifty-moves rule here, might be added later tho
        StringBuilder workingFENString = new StringBuilder(finalFENString);
        workingFENString.append(" 0 "+ GameFlow.turnCounter);
        return workingFENString.toString();
    }

}
