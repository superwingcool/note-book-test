package com.wing.test.repository;

import com.wing.test.dto.NoteBookStatus;
import com.wing.test.entity.NoteBook;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteBookRepositoryTest {

    @Autowired
    private NoteBookRepository noteBookRepository;

    @Test
    public void test_add_note_book() {
        NoteBook noteBook = new NoteBook();
        noteBook.setRowKey("333");
        noteBook.setName("笔记本2");
        noteBook.setCreateDate(new java.util.Date().getTime());
        noteBook.setUpdateDate(new java.util.Date().getTime());
        noteBook.setNoteBookStatus(NoteBookStatus.NORMAL);
        noteBookRepository.saveOrUpdate(noteBook);
    }

    @Test
    public void test_find_note_book_by_row_key() {
        List<NoteBook> noteBooks = noteBookRepository.findNoteBooksByRowKey("123");
        System.out.println(noteBooks.size());
    }

}