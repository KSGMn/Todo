import { useState } from "react";
import TodoCard from "../components/TodoCard";

const TodoList = () => {
  const [todos, setTodos] = useState([
    { id: 1, title: "Test Todo 1", content: "This is the first todo" },
    { id: 2, title: "Test Todo 2", content: "This is the second todo" },
    { id: 3, title: "Test Todo 3", content: "This is the third todo" },
    { id: 1, title: "Test Todo 1", content: "This is the first todo" },
    { id: 2, title: "Test Todo 2", content: "This is the second todo" },
    { id: 3, title: "Test Todo 3", content: "This is the third todo" },
    { id: 1, title: "Test Todo 1", content: "This is the first todo" },
    { id: 2, title: "Test Todo 2", content: "This is the second todo" },
    { id: 3, title: "Test Todo 3", content: "This is the third todo" },
    { id: 1, title: "Test Todo 1", content: "This is the first todo" },
    { id: 2, title: "Test Todo 2", content: "This is the second todo" },
    { id: 3, title: "Test Todo 3", content: "This is the third todo" },
  ]);
  //   const [title, setTitle] = useState("");
  //   const [content, setContent] = useState("");

  // Todo 생성 함수
  //   const handleAddTodo = () => {
  //     if (!title || !content) return alert("제목과 내용을 입력하세요.");

  //     const newTodo = {
  //       id: Date.now(), // 고유 ID 생성
  //       title,
  //       content,
  //       createdAt: new Date().toLocaleString(), // 생성일
  //     };

  //     setTodos([...todos, newTodo]); // Todo 리스트 업데이트
  //     setTitle(""); // 입력 필드 초기화
  //     setContent("");
  //   };

  // Todo 삭제 함수
  const handleDeleteTodo = (id) => {
    setTodos(todos.filter((todo) => todo.id !== id));
  };

  return (
    <div className="container my-4 d-flex flex-column" style={{ height: "90%" }}>
      <button className="btn btn-secondary col-2 m-auto">Todo 쓰기</button>
      <div className="d-flex flex-row gap-4 p-4 h-100" style={{ overflow: "hidden" }}>
        <div
          className="card-group d-flex justify-content-center flex-fill p-4 border border-secondary rounded-4"
          style={{ overflowY: "auto", scrollbarWidth: "none", msOverflowStyle: "none" }}
        >
          {/* 반복할 Todo 리스트 */}
          <TodoCard todos={todos} handleDeleteTodo={handleDeleteTodo} />
        </div>
        <div
          className="card-group d-flex justify-content-center flex-fill p-4 border border-secondary rounded-4"
          style={{ overflowY: "auto", scrollbarWidth: "none", msOverflowStyle: "none" }}
        >
          {/* Todo 리스트 */}
          <TodoCard todos={todos} handleDeleteTodo={handleDeleteTodo} />
        </div>
        <div
          className="card-group d-flex justify-content-center flex-fill p-4 border border-secondary rounded-4"
          style={{ overflowY: "auto", scrollbarWidth: "none", msOverflowStyle: "none" }}
        >
          {/* 완료한 Todo 리스트 */}
          <TodoCard todos={todos} handleDeleteTodo={handleDeleteTodo} />
        </div>
      </div>
    </div>
  );
};

export default TodoList;
