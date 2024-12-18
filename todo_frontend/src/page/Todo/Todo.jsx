import { Route, Routes, useNavigate } from "react-router-dom";
import TodoList from "../../layout/TodoList/TodoList";
import "./Todo.css";
import Login from "../Login/Login";
import SignUp from "../SignUp/SignUp";
import Header from "../../layout/Header/Header";
import TodoModal from "../../components/Modal/TodoModal";

const Todo = () => {
  const navigate = useNavigate();
  return (
    <div className="Todo">
      <Header />
      <main>
        <Routes>
          <Route path="/" element={<TodoList navigate={navigate} />}>
            <Route path="/add" element={<TodoModal navigate={navigate} />} />
            <Route path="/add2" element={<TodoModal navigate={navigate} />} />
          </Route>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
        </Routes>
      </main>
    </div>
  );
};

export default Todo;
