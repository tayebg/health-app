import React, { createContext, useContext, useState, ReactNode } from 'react';

interface User {
  id: number;
  user: string;
  fullName: string;
  gender: string;
  email: string;
}

interface UserContextType {
  users: User[];
  updateUser: (id: number, userData: Omit<User, 'id'>) => void;
  deleteUser: (id: number) => void;
  addUser: (userData: Omit<User, 'id'>) => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const useUsers = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUsers must be used within a UserProvider');
  }
  return context;
};

export const UserProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [users, setUsers] = useState<User[]>([
    {
      id: 1,
      user: "john123",
      fullName: "John Doe", 
      gender: "Male",
      email: "john@example.com"
    },
    {
      id: 2,
      user: "jane456",
      fullName: "Jane Smith",
      gender: "Female", 
      email: "jane@example.com"
    }
  ]);

  const updateUser = (id: number, userData: Omit<User, 'id'>) => {
    setUsers(prev => prev.map(user => 
      user.id === id ? { ...userData, id } : user
    ));
  };

  const deleteUser = (id: number) => {
    setUsers(prev => prev.filter(user => user.id !== id));
  };

  const addUser = (userData: Omit<User, 'id'>) => {
    const newId = Math.max(...users.map(u => u.id), 0) + 1;
    setUsers(prev => [...prev, { ...userData, id: newId }]);
  };

  return (
    <UserContext.Provider value={{ users, updateUser, deleteUser, addUser }}>
      {children}
    </UserContext.Provider>
  );
};