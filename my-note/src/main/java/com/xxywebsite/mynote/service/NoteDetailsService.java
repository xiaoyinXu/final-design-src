package com.xxywebsite.mynote.service;


import com.xxywebsite.mynote.bean.NoteQueryBean;
import com.xxywebsite.mynote.entity.NoteDetails;
import com.xxywebsite.mynote.es.EsNote;
import com.xxywebsite.mynote.mapper.NoteDetailsMapper;
import com.xxywebsite.mynote.util.KafkaProducerUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NoteDetailsService {
//    private static final Logger logger = LoggerFactory.getLogger(NoteDetailsService.class);


    @Autowired
    NoteDetailsMapper noteDetailsMapper;

    @Autowired
    EsNoteDetailsService esNoteDetailsService;

    @CacheEvict(cacheNames = "menu", key = "#userId")
    public boolean addNoteDetails(NoteDetails noteDetails, int userId) {
        // 往es里面添加noteDetails
        // 但是这里存在顺序问题, 刚开始的noteDetails没有id
        // let's see
        EsNote esNote = new EsNote();
        boolean result = noteDetailsMapper.addNoteDetails(noteDetails);
        if (result) {
            BeanUtils.copyProperties(noteDetails, esNote);
            esNoteDetailsService.addNote(esNote);
        }

        // kafka



        return result;
    }
    @CacheEvict(cacheNames = "menu", key = "#userId")
    public boolean deleteNoteDetails(int id, int userId) {
        return noteDetailsMapper.deleteNoteDetails(id);

    }
    @CacheEvict(cacheNames = "menu", key = "#userId")
    public boolean updateNoteDetails(NoteDetails noteDetails, int userId) {
        // ES 可能没有这个元素
        try {
            esNoteDetailsService.updateNoteContent(noteDetails.getId(), noteDetails.getContent());
        } catch (Exception e) {
            System.out.println("ES 没有对应信息");;
        }

        boolean success = noteDetailsMapper.updateNoteDetails(noteDetails);

        int noteId = noteDetails.getId();
        String behavior = "update";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        String msg = String.format("%d,%d,%s,%s",userId, noteId, behavior, time);
        KafkaProducerUtils.send("note-info",msg);

        return success;
    }
    public NoteDetails findNoteDetailsById(int id, Integer userId) {

        NoteDetails noteDetailsById = noteDetailsMapper.findNoteDetailsById(id);
        int noteId = id;
        String behavior = "access";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        String msg = String.format("%d,%d,%s,%s",userId, noteId, behavior, time);
        KafkaProducerUtils.send("note-info",msg);
        return noteDetailsById;

    }

    public List<NoteDetails> findNoteDetailsByNoteQueryBean(NoteQueryBean noteQueryBean) {
        return noteDetailsMapper.findNoteDetailsByNoteQueryBean(noteQueryBean);
    }
}
