import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios'; // Import axios

// --- Axios Setup ---
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api'; // Use environment variable or default

const apiClient = axios.create({
  baseURL: API_BASE_URL,
});

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken'); // Assuming token is stored with key 'authToken'
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
// --- End Axios Setup ---

// Interface definitions
export interface User {
  id: number;
  username: string;
  email: string;
  highest_score: number;
  created_at: string;
}

export interface GameHistory {
  id: number;
  user_id: number;
  username: string; // Added for convenience, from GameHistoryResponseDto
  score: number;
  played_at: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean; // Added to manage loading state during auth operations
  login: (usernameOrEmail: string, password: string) => Promise<void>;
  register: (username: string, email: string, password: string) => Promise<void>;
  logout: () => void;
  fetchCurrentUser: () => Promise<User | null>;
  // Add other auth-related functions if needed, e.g., fetchGameHistory
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(localStorage.getItem('authToken'));
  const [isLoading, setIsLoading] = useState<boolean>(true); // Start with loading true

  useEffect(() => {
    const attemptAutoLogin = async () => {
      setIsLoading(true);
      if (token) {
        // apiClient (from above) will automatically use this token for requests
        try {
          // This is where you'd typically call your /api/users/me endpoint
          // For now, we'll simulate or use a placeholder if actual API call isn't part of this step
          // console.log("Attempting to fetch current user with token:", token);
          // const response = await apiClient.get('/users/me'); // Example API call
          // setUser(response.data);
          // If /users/me is not yet implemented or tested with apiClient, use placeholder:
          const storedUser = localStorage.getItem('user');
          if (storedUser) {
            setUser(JSON.parse(storedUser));
          } else {
            // If no user in local storage, but token exists, try to fetch.
            // This part will be more robust when API calls are integrated.
            // For now, if token exists but no user, we might clear token or try fetch
            await fetchCurrentUser(); // Try to fetch if token exists
          }
        } catch (error) {
          console.error('Auto login failed:', error);
          localStorage.removeItem('authToken');
          localStorage.removeItem('user');
          setToken(null);
          setUser(null);
        }
      }
      setIsLoading(false);
    };
    attemptAutoLogin();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [token]); // Re-run if token changes (e.g., after login/logout)

  const login = async (usernameOrEmail: string, password: string) => {
    setIsLoading(true);
    // Mock login - replace with actual API call
    console.log("Attempting login with:", usernameOrEmail);
    return new Promise<void>((resolve, reject) => {
      setTimeout(() => {
        const mockToken = 'fake-jwt-token-for-' + usernameOrEmail;
        const mockUser: User = {
          id: 1,
          username: usernameOrEmail,
          email: usernameOrEmail.includes('@') ? usernameOrEmail : `${usernameOrEmail}@example.com`,
          highest_score: 0,
          created_at: new Date().toISOString(),
        };
        localStorage.setItem('authToken', mockToken);
        localStorage.setItem('user', JSON.stringify(mockUser));
        setToken(mockToken);
        setUser(mockUser);
        setIsLoading(false);
        resolve();
      }, 1000);
    });
  };

  const register = async (username: string, email: string, password: string) => {
    setIsLoading(true);
    // Mock register - replace with actual API call
    console.log("Attempting registration for:", username, email);
    return new Promise<void>((resolve, reject) => {
      setTimeout(() => {
        // Simulate success, but in a real app, you might want to auto-login
        // or require user to login after registration.
        // For this mock, we don't auto-login or set user/token.
        console.log(`Mock registration successful for ${username}. Please login.`);
        setIsLoading(false);
        resolve();
        // To simulate auto-login:
        // const mockToken = 'fake-jwt-token-for-' + username;
        // const mockUser: User = { id: Date.now(), username, email, highest_score: 0, created_at: new Date().toISOString() };
        // localStorage.setItem('authToken', mockToken);
        // localStorage.setItem('user', JSON.stringify(mockUser));
        // setToken(mockToken);
        // setUser(mockUser);
      }, 1000);
    });
  };

  const logout = () => {
    setIsLoading(true); // Optional: set loading true during logout
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    setToken(null);
    setUser(null);
    // apiClient.defaults.headers.common['Authorization'] = ''; // Clear auth header for this instance if needed
    // However, the interceptor should handle not adding it if token is null
    setIsLoading(false);
    // Optionally redirect or perform other cleanup
  };

  const fetchCurrentUser = async (): Promise<User | null> => {
    const currentToken = localStorage.getItem('authToken');
    if (!currentToken) {
      setUser(null); // Ensure user state is cleared if no token
      return null;
    }
    // apiClient will use the token from localStorage via interceptor
    try {
      // console.log("Fetching current user with apiClient");
      // const response = await apiClient.get<User>('/users/me'); // Actual API call
      // setUser(response.data);
      // localStorage.setItem('user', JSON.stringify(response.data)); // Update user in localStorage
      // return response.data;

      // Mock implementation for fetchCurrentUser if API call not ready
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
         const parsedUser = JSON.parse(storedUser);
         setUser(parsedUser);
         return parsedUser;
      }
      // If no user in local storage, but token exists, this indicates an inconsistent state
      // or that the user data needs to be fetched.
      // For mock, return null or throw error if no storedUser.
      // In a real app, /users/me should be the source of truth if token is valid.
      console.warn("fetchCurrentUser: No user in localStorage, but token exists. Clearing token.");
      logout(); // Clear inconsistent state
      return null;

    } catch (error) {
      console.error('Failed to fetch current user:', error);
      logout(); // Important: clear token and user if fetch fails (e.g. token expired)
      return null;
    }
  };


  return (
    <AuthContext.Provider value={{ user, token, isAuthenticated: !!user, isLoading, login, register, logout, fetchCurrentUser }}>
      {children}
    </AuthContext.Provider>
  );
};
