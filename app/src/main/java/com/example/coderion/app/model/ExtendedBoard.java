package com.example.coderion.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by coderion on 20.11.17.
 */

public class ExtendedBoard implements Serializable {
    private Board board;

    private byte[] img;

    private List<Long> ids;

    public ExtendedBoard(List<Long> ids) {
        this.ids = ids;
    }

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

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
