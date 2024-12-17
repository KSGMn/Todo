import { Route, Routes } from "react-router-dom";
import TodoList from "../layout/TodoList";
import "./Todo.css";
import Login from "./Login/Login";
import SignUp from "./SignUp/SignUp";
import Header from "../layout/Header/Header";

const Todo = () => {
  return (
    <div className="Todo">
      <Header />
      <main>
        <Routes>
          <Route path="/" element={<TodoList />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
        </Routes>
      </main>
    </div>
  );
};

export default Todo;
