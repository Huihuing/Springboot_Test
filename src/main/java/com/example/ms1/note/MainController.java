package com.example.ms1.note;

import com.example.ms1.note.Notebook.Notebook;
import com.example.ms1.note.Notebook.NotebookRepository;
import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteRepository;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NoteRepository noteRepository;
    private final NotebookRepository notebookRepository;
    private final NoteService noteService;

    @RequestMapping("/")
    public String main(Model model) {

        List<Notebook> notebookList = notebookRepository.findAll();
        if (notebookList.isEmpty()) {
            Notebook notebook = new Notebook();
            notebook.setName("새 문서");
            noteRepository.save();

            return "redirect:/";
        }
        Notebook targetNotebook = notebookList.get(0);

        List<Note> noteList = noteRepository.findByNotebook(targetNotebook);
        if (noteList.isEmpty()) {
            noteService.saveDefault(note);
            return "redirect:/";
        }

        model.addAttribute("notebookList", notebookList);
        model.addAttribute("targetNotebook", targetNotebook);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("noteList", noteList);
        return "main";
    }
}

//@RequestMapping("/")
//public String main(Model model) {
//    //1. DB에서 데이터 꺼내오기
//    List<Note> noteList = noteRepository.findAll();
//
//    //2. 꺼내온 데이터를 템플릿으로 보내기
//    model.addAttribute("noteList", noteList);
//    model.addAttribute("targetNote", noteList.get(0));
//
//    return "main";
//}





//@PostMapping("/write")
//public String write(@PathVariable("notebookId") Long notebookId) {
//    Notebook notebook = notebookRepository.findAll();
//    noteService.saveDefault(notebook);
//    return "redirect:/";
////        Note note = new Note();
////        note.setTitle("new title..");
////        note.setContent("");
////        note.setCreateDate(LocalDateTime.now());
////
////        noteRepository.save(note);
////
////        return "redirect:/";
//}
//
//@GetMapping("/detail/{id}")
//public String detail(Model model, @PathVariable Long id) {
//
//
////        Note note = noteRepository.findById(id).get();
////        model.addAttribute("targetNote", note);
////        model.addAttribute("noteList", noteRepository.findAll());
////
////        return "main";
//}
//@PostMapping("/update")
//public String update(Long id, String title, String content) {
//
//
////        Note note = noteRepository.findById(id).get();
////        note.setTitle(title);
////        note.setContent(content);
////
////        noteRepository.save(note);
////        return "redirect:/detail/" + id;
//}
//
//
