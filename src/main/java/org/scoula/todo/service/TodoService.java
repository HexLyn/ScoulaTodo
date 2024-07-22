package org.scoula.todo.service;

import org.scoula.todo.context.Context;
import org.scoula.todo.dao.TodoDao;
import org.scoula.todo.dao.TodoDaoImpl;
import org.scoula.todo.domain.TodoVO;
import org.scoula.todo.ui.Input;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class TodoService {
    TodoDao dao = new TodoDaoImpl();

    Supplier<String> userId = () -> Context.getContext().getUser().getId();

    public void print() {
        try {
            int count = dao.getTotalCount(userId.get());
            List<TodoVO> list = dao.getList(userId.get());

            System.out.println("Total Count: " + count);
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmm");
            for (TodoVO todo : list) {
                System.out.printf("%03d] %s%s\n", todo.getId(), todo.getTitle(),
                        todo.getDone() ? "(완료)" : "");
            }
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void serach() {
        String keyword = Input.getLine("search : ", "default");
        keyword = "%" + keyword + "%";
        try {
            List<TodoVO> list = dao.search(userId.get(), keyword);
            System.out.println("total Count: " + list.size());

            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmm");
            for (TodoVO todo : list) {
                System.out.printf("%03d] %s%s\n", todo.getId(), todo.getTitle(),
                        todo.getDone() ? "(완료)" : "");
                System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmm");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void detail() {
        long id = (long) Input.getInt("Todo ID: ");
        try {
            TodoVO todo = dao.get(userId.get(), id).orElseThrow(NoSuchElementException::new);
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
            System.out.printf("Todo ID :%s\n", todo.getId());
            System.out.printf("Title : %s\n", todo.getTitle());
            System.out.printf("Desc : %s\n", todo.getDescription());
            System.out.printf("Boolean : %s\n", todo.getDone() ? "success" : "not yet");
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmm");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create() {
        String title = Input.getLine("title : ", "default");
        String description = Input.getLine("description : ", "default");

        TodoVO todo = TodoVO.builder()
                .title(title)
                .description(description)
                .userId(userId.get())
                .done(false)
                .build();
        try {
            dao.create(todo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        Long id = (long)Input.getInt("Update ID: ");
        try {
            TodoVO todo = dao.get(userId.get(), id).orElseThrow(NoSuchElementException::new);
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
            String title = Input.getLine("title : ", todo.getTitle());
            String description = Input.getLine("description : ", todo.getDescription());
            boolean done = Input.confirm("Confirm", todo.getDone());
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");

            todo.setTitle(title);
            todo.setDescription(description);
            todo.setDone(done);

            dao.update(userId.get(), todo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        Long id = (long)Input.getInt("delete ID : ");
        boolean answer = Input.confirm("delete?");
        if(answer) {
            try {
                dao.delete(userId.get(), id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}