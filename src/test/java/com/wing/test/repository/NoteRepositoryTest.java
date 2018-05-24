package com.wing.test.repository;

import com.wing.test.entity.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Test
    public void test_add_note() {
        Note note = new Note();
        note.setRowKey("333");
        note.setName("笔记本3");
        note.setCreateDate(new java.util.Date().getTime());
        note.setUpdateDate(new java.util.Date().getTime());
        note.setContent("这是测试文本");
        noteRepository.saveOrUpdate(note);
    }


    @Test
    public void test_get_note_name() {
        String name = noteRepository.getNoteName("333_1527131273791");
        System.out.println(name);
    }

    @Test
    public void test_get_notes() {
        List<Note> notes = noteRepository.findNoteBasicInfos("333");
        for (Note note : notes) {
            System.out.println(note.getName());
        }
    }

    @Test
    public void test_get_note_content() {
        String content = noteRepository.getNoteContent("333_1527131273791");
        System.out.println(content);
    }


}