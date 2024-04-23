package com.example.ms1.Note.Note;

import com.example.ms1.Note.Notebook.Notebook;
import com.example.ms1.Note.Notebook.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{notebookId}/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final NotebookRepository notebookRepository;
    private final NoteService noteService;

    @PostMapping("/write")
    public String write(@PathVariable("notebookId") Long notebookId) {
        Notebook notebook = notebookRepository.findById(notebookId).orElseThrow();
        noteService.saveDefault(notebook);
        return "redirect:/" + notebookId + "/notes";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("notebookId") Long notebookId, @PathVariable("id") Long id) {
        Note note = noteRepository.findById(id).orElseThrow();

        List<Notebook> notebookList = notebookRepository.findAll();
        Notebook targetNotebook = notebookRepository.findById(notebookId).orElseThrow();
        List<Note> noteList = noteRepository.findByNotebook(targetNotebook);

        model.addAttribute("notebookList", notebookList);
        model.addAttribute("targetNotebook", targetNotebook);
        model.addAttribute("targetNote", note);
        model.addAttribute("noteList", noteList);

        return "main";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("notebookId") Long notebookId, @PathVariable("id") Long id, String title, String content) {
        Note note = noteRepository.findById(id).orElseThrow();

        if (title.trim().length() == 0) {
            title = "title";
        }

        note.setTitle(title);
        note.setContent(content);

        noteRepository.save(note);
        return "redirect:/books/" + notebookId + "/notes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("notebookId") Long notebookId, @PathVariable("id") Long id) {
        noteRepository.deleteById(id);
        return "redirect:/books/" + notebookId + "/notes"  + id;
    }

}
