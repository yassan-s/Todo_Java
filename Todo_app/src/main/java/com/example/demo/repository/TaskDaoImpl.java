package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskType;

@Repository
public class TaskDaoImpl implements TaskDao {

	// DB操作用のクラス
	// DIでnewされている
	private final JdbcTemplate jdbcTemplate;

	// JdbcTemplateを読み込む
	// DIコンテナで作成されたインスタンスを格納する
	@Autowired
	public TaskDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 一覧表示用
	 */
	@Override
	public List<Task> findAll() {

		String sql = "SELECT task.id, user_id, type_id, title, detail, deadline, "
				+ "type, comment FROM task "
				+ "LEFT JOIN task_type ON task.type_id = task_type.id";

		// Task一覧をMapのListで取得
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

		// return 用の空のListを用意
		List<Task> list = new ArrayList<Task>();

		// 2つのテーブルのデータをTaskにまとめる
		for(Map<String, Object> result : resultList) {

			// entityのTask
			Task task = new Task();
			task.setId((int)result.get("id"));
			task.setUserId((int)result.get("user_id"));
			task.setTypeId((int)result.get("type_id"));
			task.setTitle((String)result.get("title"));
			task.setDetail((String)result.get("detail"));
			// toLocalDateTime で Datetime型に変換
			task.setDeadline(((Timestamp)result.get("deadline")).toLocalDateTime());

			// entityのTaskType
			TaskType type = new TaskType();
			type.setId((int)result.get("type_id"));
			type.setType((String)result.get("type"));
			type.setComment((String)result.get("comment"));

			// Task に TaskTypeをセット
			task.setTaskType(type);
			list.add(task);
		}
		return list;
	}

	/**
	 * id による1件のみの取得
	 */
	@Override
	public Optional<Task> findById(int id) {

		// SQLインジェクションを防ぐ
		String sql = "SELECT task.id, user_id, type_id, title, detail, deadline, "
				+ "type, comment FROM task "
				+ "LEFT JOIN task_type ON task.type_id = task_type.id "
				+ "WHERE task.id = ?";

		// Task一覧をMapのListで取得
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

		// entityのTask
		Task task = new Task();
		task.setId((int)result.get("id"));
		task.setUserId((int)result.get("user_id"));
		task.setTypeId((int)result.get("type_id"));
		task.setTitle((String)result.get("title"));
		task.setDetail((String)result.get("detail"));
		// toLocalDateTime で Datetime型に変換
		task.setDeadline(((Timestamp)result.get("deadline")).toLocalDateTime());

		// entityのTaskType
		TaskType type = new TaskType();
		type.setId((int)result.get("type_id"));
		type.setType((String)result.get("type"));
		type.setComment((String)result.get("comment"));

		// TaskにTaskTypeをセット
		task.setTaskType(type);

		// TaskをOptionalでラップする
		// nullになっているかもしれない合図
		Optional<Task> taskOpt = Optional.ofNullable(task);
		return taskOpt;
	}

	@Override
	public void insert(Task task) {
		jdbcTemplate.update("INSERT INTO task(user_id, type_id, title, detail, deadline) VALUES(?, ?, ?, ?,?)",
				 task.getUserId(), task.getTypeId(), task.getTitle(), task.getDetail(), task.getDeadline() );
	}

	// updateの場合、更新相手がないと「０」を返してくる
	// 例外処理をServiceで設定するため
	@Override
	public int update(Task task) {
		return jdbcTemplate.update("UPDATE task SET type_id = ?, title = ?, detail = ?,deadline = ? WHERE id = ?",
				task.getTypeId(), task.getTitle(), task.getDetail(), task.getDeadline(), task.getId() );
	}

	@Override
	public int deleteById(int id) {
		return jdbcTemplate.update("DELETE FROM task WHERE id = ?", id);
	}


}
