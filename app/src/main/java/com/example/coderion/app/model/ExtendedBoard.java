package com.example.coderion.app.model;

import java.io.Serializable;

/**
 * Created by coderion on 20.11.17.
 */

public class ExtendedBoard implements Serializable {
    private Board board;

    private byte[] img;

    public ExtendedBoard(Board board, byte[] img) {
        this.board = board;
        this.img = img;
    }

    public ExtendedBoard() {
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
