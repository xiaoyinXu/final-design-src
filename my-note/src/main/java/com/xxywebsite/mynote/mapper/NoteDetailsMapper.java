package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.bean.NoteQueryBean;
import com.xxywebsite.mynote.entity.NoteDetails;

import java.util.List;

public interface NoteDetailsMapper {
    //增删改查
    boolean addNoteDetails(NoteDetails noteDetails);
    boolean deleteNoteDetails(int id);
    boolean updateNoteDetails(NoteDetails noteDetails);
    NoteDetails findNoteDetailsById(int id);
    List<NoteDetails> findNoteDetailsByNoteQueryBean(NoteQueryBean noteQueryBean);

}
