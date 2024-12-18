import { useState } from "react";
import TodoCard from "../../components/TodoCard";
import { Outlet } from "react-router-dom";
import PropTypes from "prop-types";

const TodoList = ({ navigate }) => {
  const [todos, setTodos] = useState([
    {
      id: 1,
      title: "Test Todo 1",
      content: "This is the first Todo",
      createdAt: "2024-12-25",
    },
    { id: 2, title: "Test Todo 2", content: "This is the second todo", createdAt: "2024-12-25" },
    { id: 3, title: "Test Todo 3", content: "This is the third todo", createdAt: "2024-12-25" },
    { id: 4, title: "Test Todo 1", content: "This is the first todo", createdAt: "2024-12-25" },
    { id: 5, title: "Test Todo 2", content: "This is the second todo", createdAt: "2024-12-25" },
    { id: 6, title: "Test Todo 3", content: "This is the third todo", createdAt: "2024-12-25" },
    { id: 7, title: "Test Todo 1", content: "This is the first todo", createdAt: "2024-12-25" },
    { id: 8, title: "Test Todo 2", content: "This is the second todo", createdAt: "2024-12-25" },
    { id: 9, title: "Test Todo 3", content: "This is the third todo", createdAt: "2024-12-25" },
    { id: 10, title: "Test Todo 1", content: "This is the first todo", createdAt: "2024-12-25" },
    { id: 11, title: "Test Todo 2", content: "This is the second todo", createdAt: "2024-12-25" },
    { id: 12, title: "Test Todo 3", content: "This is the third todo", createdAt: "2024-12-25" },
  ]);
  const [showModal, setShowModal] = useState(false); // eslint-disable-line no-unused-vars

  // Todo 삭제 함수
  const handleDeleteTodo = (id) => {
    setTodos(todos.filter((todo) => todo.id !== id));
  };

  const handleOpenModal = (type) => {
    setShowModal(true);
    if (type === "반복") {
      navigate("/add2");
    } else {
      navigate("/add");
    }
    return;
  };

  return (
    <div className="container my-4 d-flex flex-column" style={{ height: "90%" }}>
      <div className="d-flex justify-content-center gap-5">
        <button className="btn btn-secondary col-2" onClick={() => handleOpenModal()}>
          Todo 추가
        </button>
        <button className="btn btn-secondary col-2" onClick={() => handleOpenModal("반복")}>
          반복할 Todo 추가
        </button>
      </div>
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
      <div className="modal" style={{ position: "relative" }}>
        <Outlet context={{ showModal, setShowModal }} />
      </div>
    </div>
  );
};

TodoList.propTypes = {
  navigate: PropTypes.func.isRequired,
};

export default TodoList;
