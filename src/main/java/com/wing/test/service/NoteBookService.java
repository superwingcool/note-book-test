package com.wing.test.service;

import com.wing.test.entity.NoteBook;
import com.wing.test.repository.NoteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteBookService {

    @Autowired
    private NoteBookRepository noteBookRepository;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private RedisService redisService;


    public void addNewNoteBook(NoteBook noteBook) {
        noteBookRepository.saveOrUpdate(noteBook);
        threadPoolTaskExecutor.execute(() -> flushCacheForNoteBook(noteBook.getRowKey()));
    }

    public void deleteNoteBook(String rowKey) {
        noteBookRepository.deleteNoteBook(rowKey);
        threadPoolTaskExecutor.execute(() -> flushCacheForNoteBook(rowKey.split("_")[0]));
    }

    @Cacheable(value= "note_book", key = "#rowKey")
    public List<NoteBook> findNoteBooksByRowKey(String rowKey) {
        return noteBookRepository.findNoteBooksByRowKey(rowKey);
    }


    private void flushCacheForNoteBook(String rowKey) {
        List<NoteBook> noteBooks = noteBookRepository.findNoteBooksByRowKey(rowKey);
        redisService.setExpire(rowKey, noteBooks);
    }

}
