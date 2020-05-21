package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.Menu;
import com.xxywebsite.mynote.entity.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;


public interface NoteMapper {
    Note findNoteById(int id);
    List<Note> findNotesByMenuId(int menuId);
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") //为了获得新增主键
    @Insert("insert into note(name, menu_id) values(#{name},#{menuId})")
    boolean addNote(Note note);


    boolean updateNote(Note note);

    boolean deleteNote(int id);
}
