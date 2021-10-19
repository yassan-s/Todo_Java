package com.example.demo.app.task;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;

/**
 * ToDoアプリ
 */
@Controller
@RequestMapping("/task")
public class TaskController {

	private final TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

    /**
     * タスクの一覧を表示する
     * @param taskForm
     * @param model リクエストスコープ
     * @return resources/templates下のHTMLファイル名
     */
    @GetMapping
    public String task(TaskForm taskForm, Model model) {

    	// 新規登録か更新かを判断する
    	taskForm.setNewTask(true);

    	// Taskリストを取得する
    	List<Task>list = taskService.findAll();

    	model.addAttribute("list", list);
    	model.addAttribute("title", "タスク一覧");

    	return "task/index";

    }

    /**
     * タスクデータを1件挿入
     * @param taskForm
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute TaskForm taskForm,
    	BindingResult result,
    	Model model) {

    	// TaskFormのデータをTaskに格納
    	// 共通処理として下にmakeTaskメソッドを記載
    	Task task = makeTask(taskForm, 0);

        if (!result.hasErrors()) {
        	// 問題ない場合
        	//1件挿入後リダイレクトする
            taskService.insert(task);
            return "redirect:/task";
        } else {
        	// エラーの場合
            taskForm.setNewTask(true);
            model.addAttribute("taskForm", taskForm);
            List<Task> list = taskService.findAll();
            model.addAttribute("list", list);
            model.addAttribute("title", "タスク一覧（バリデーション）");
            return "task/index";
        }
	}


    /**
     * TaskFormのデータをTaskに入れて返す
     * @param taskForm
     * @param taskId 新規登録の場合は0を指定
     * @return
     */
    private Task makeTask(TaskForm taskForm, int taskId) {
    	Task task = new Task();
        if(taskId != 0) {
        	task.setId(taskId);
        }
        task.setUserId(1);
        task.setTypeId(taskForm.getTypeId());
        task.setTitle(taskForm.getTitle());
        task.setDetail(taskForm.getDetail());
        task.setDeadline(taskForm.getDeadline());
        return task;
    }

    /**
     * TaskのデータをTaskFormに入れて返す
     * @param task
     * @return
     */
    private TaskForm makeTaskForm(Task task) {

        TaskForm taskForm = new TaskForm();

        taskForm.setTypeId(task.getTypeId());
        taskForm.setTitle(task.getTitle());
        taskForm.setDetail(task.getDetail());
        taskForm.setDeadline(task.getDeadline());
        taskForm.setNewTask(false);

        return taskForm;
    }
}
