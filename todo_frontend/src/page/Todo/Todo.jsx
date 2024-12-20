import { Route, Routes, useNavigate } from "react-router-dom";
import TodoList from "../../layout/TodoList/TodoList";
import "./Todo.css";
import Login from "../Login/Login";
import SignUp from "../SignUp/SignUp";
import Header from "../../layout/Header/Header";
import TodoModal from "../../components/Modal/TodoModal";
import { useEffect, useState } from "react";
import { findAllTodo } from "../../api";

const Todo = () => {
  const [todos, setTodos] = useState([]);

  const navigate = useNavigate();
  // 모든 Todo 데이터 가져오기
  useEffect(() => {
    const findAllTodoResponse = async () => {
      try {
        const data = await findAllTodo();
        setTodos(Array.isArray(data.data.data) ? data.data.data : []);
      } catch (error) {
        console.log(error);
        return [];
      }
    };
    findAllTodoResponse();
  }, []);
  return (
    <div className="Todo">
      <Header />
      <main>
        <Routes>
          <Route path="/" element={<TodoList navigate={navigate} todos={todos} setTodos={setTodos} />}>
            <Route path="/add" element={<TodoModal navigate={navigate} todos={todos} setTodos={setTodos} />} />
            <Route path="/add2" element={<TodoModal navigate={navigate} todos={todos} setTodos={setTodos} />} />
          </Route>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
        </Routes>
      </main>
    </div>
  );
};

export default Todo;
