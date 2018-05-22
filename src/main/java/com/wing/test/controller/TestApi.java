package com.wing.test.controller;

import com.wing.test.dto.NoteBookStatus;
import com.wing.test.entity.NoteBook;
import com.wing.test.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestApi {

    @Autowired
    private NoteBookService noteBookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNoteBook() {
        NoteBook noteBook = new NoteBook();
        noteBook.setRowKey("123");
        noteBook.setName("笔记本1");
        noteBook.setCreateDate(new java.util.Date().getTime());
        noteBook.setUpdateDate(new java.util.Date().getTime());
        noteBook.setNoteBookStatus(NoteBookStatus.NORMAL);
        noteBookService.addNewNoteBook(noteBook);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteBook> getNoteBooks() {
        return noteBookService.findNoteBooksByRowKey("123");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteBook> deleteNoteBook() {
        return noteBookService.findNoteBooksByRowKey("123_1526894234864");
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateNoteBook() {
        NoteBook noteBook = new NoteBook();
        noteBook.setRowKey("123_1526894301223");
        noteBook.setName("笔记本222");
        noteBook.setUpdateDate(new java.util.Date().getTime());
        noteBook.setNoteBookStatus(NoteBookStatus.NORMAL);
        noteBookService.addNewNoteBook(noteBook);
    }
}


