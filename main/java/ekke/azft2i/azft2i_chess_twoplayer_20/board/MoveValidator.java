package ekke.azft2i.azft2i_chess_twoplayer_20.board;

import android.graphics.Point;
import android.util.Log;

import java.util.Optional;
import java.util.Set;

import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.ChessPiece;
import ekke.azft2i.azft2i_chess_twoplayer_20.pieces.Color;

public class MoveValidator {

    public boolean isAttackMove(int newX, int newY, ChessPiece[][] board, ChessPiece chessPiece) {
        if (board[newX][newY] != null && board[newX][newY].getColor() != chessPiece.getColor()) {
            return true;
        }
        return false;
    }

    public boolean isAttackModeAvailable(ChessPiece[][] board, boolean isWhiteMove) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                Color activeColor = isWhiteMove ? Color.WHITE : Color.BLACK;
                ChessPiece currentCell = board[x][y];
                if (currentCell != null && currentCell.getColor().equals(activeColor)) {
                    Set<Point> possibleAllMoves = currentCell.getPossibleAllMoves(board);
                    Optional<Point> first = possibleAllMoves.stream()
                            .filter(possibleMove -> isAttackMove(possibleMove.x, possibleMove.y, board, currentCell))
                            .findFirst();
                    if (first.isPresent()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Color isWinner(ChessPiece[][] board){
        boolean whitePieceFound = false;
        boolean blackPieceFound = false;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                ChessPiece currentCell = board[x][y];
                if (currentCell != null && currentCell.getColor().equals(Color.WHITE)) {
                    whitePieceFound = true;
                }else if(currentCell != null && currentCell.getColor().equals(Color.BLACK)){
                    blackPieceFound = true;
                }
            }
        }
        if (blackPieceFound ^ whitePieceFound){//ha csak az egyik teljesül (kizáró vagy){
            if(blackPieceFound) return Color.WHITE;
            if(whitePieceFound) return Color.BLACK;
        }
        return null;
    }

    public boolean isGameTied(ChessPiece[][] board, boolean isWhiteMove){
        Color activeColor = isWhiteMove ? Color.WHITE : Color.BLACK;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

                if (board[x][y] != null && board[x][y].getColor().equals(activeColor)) {
                    Set<Point> possibleAllMoves = board[x][y].getPossibleAllMoves(board);
                    if (!possibleAllMoves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        Log.d("MoveValidator", "Az aktív játékos nem tud mozogni");
        return true;
    }

}
