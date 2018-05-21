package com.wing.test.service;

import com.wing.test.dto.NoteBookStatus;
import com.wing.test.entity.NoteBook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteBookServiceTest {

    @Autowired
    private NoteBookService noteBookService;

    @Test
    public void test_add_note_book() {
        NoteBook noteBook = new NoteBook();
        noteBook.setRowKey("555");
        noteBook.setName("笔记本1");
        noteBook.setCreateDate(new java.util.Date().getTime());
        noteBook.setUpdateDate(new java.util.Date().getTime());
        noteBook.setNoteBookStatus(NoteBookStatus.NORMAL);
        noteBookService.addNewNoteBook(noteBook);
    }

}