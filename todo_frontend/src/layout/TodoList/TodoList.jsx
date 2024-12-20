import { useEffect, useState } from "react";
import TodoCard from "../../components/TodoCard";
import { Outlet } from "react-router-dom";
import PropTypes from "prop-types";
import { deleteTodo } from "../../api";

const TodoList = ({ navigate, todos, setTodos }) => {
  const [showModal, setShowModal] = useState(false); // eslint-disable-line no-unused-vars
  const [recycledTodos, setRecycledTodos] = useState([]); // recycle이 true인 리스트
  const [pendingTodos, setPendingTodos] = useState([]); // recycle이 false이고 done이 false인 리스트
  const [doneTodos, setDoneTodos] = useState([]); // recycle이 false이고 done이 true인 리스트

  useEffect(() => {
    if (!Array.isArray(todos)) {
      setRecycledTodos([]);
      setDoneTodos([]);
      setPendingTodos([]);
      return;
    }
    setRecycledTodos(todos.filter((todo) => todo.recycle === true));
    setPendingTodos(todos.filter((todo) => todo.recycle === false && todo.done === false));
    setDoneTodos(todos.filter((todo) => todo.recycle === false && todo.done === true));
  }, [todos]);

  // Todo 삭제 api 요청
  const deleteTodoResponse = async (id) => {
    try {
      return await deleteTodo(id);
    } catch (error) {
      console.log(error);
      return [];
    }
  };

  // Todo 삭제 함수
  const handleDeleteTodo = (id) => {
    deleteTodoResponse(id);
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
          <TodoCard todos={recycledTodos} handleDeleteTodo={handleDeleteTodo} />
        </div>
        <div
          className="card-group d-flex justify-content-center flex-fill p-4 border border-secondary rounded-4"
          style={{ overflowY: "auto", scrollbarWidth: "none", msOverflowStyle: "none" }}
        >
          {/* Todo 리스트 */}
          <TodoCard todos={pendingTodos} handleDeleteTodo={handleDeleteTodo} />
        </div>
        <div
          className="card-group d-flex justify-content-center flex-fill p-4 border border-secondary rounded-4"
          style={{ overflowY: "auto", scrollbarWidth: "none", msOverflowStyle: "none" }}
        >
          {/* 완료한 Todo 리스트 */}
          <TodoCard todos={doneTodos} handleDeleteTodo={handleDeleteTodo} />
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
  todos: PropTypes.array.isRequired,
  setTodos: PropTypes.func.isRequired,
};

export default TodoList;
