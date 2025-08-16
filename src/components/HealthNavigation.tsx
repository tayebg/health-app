import { Button } from "@/components/ui/button";
import { ArrowLeft, Home, User, Activity } from "lucide-react";
import { useNavigate } from "react-router-dom";

interface HealthNavigationProps {
  showBackButton?: boolean;
  title?: string;
}

export const HealthNavigation = ({ showBackButton = false, title }: HealthNavigationProps) => {
  const navigate = useNavigate();

  return (
    <nav className="health-gradient-dark p-2 md:p-4 shadow-lg">
      <div className="container mx-auto flex items-center justify-between">
        <div className="flex items-center space-x-2 md:space-x-4">
          {showBackButton && (
            <Button 
              variant="ghost" 
              size="sm"
              onClick={() => navigate(-1)}
              className="text-white hover:bg-white/20 p-1 md:p-2"
            >
              <ArrowLeft className="h-4 w-4" />
              <span className="hidden md:ml-2 md:inline">Back</span>
            </Button>
          )}
          {title && (
            <h1 className="text-lg md:text-xl font-bold text-white truncate">{title}</h1>
          )}
        </div>
        
        <div className="flex items-center space-x-1">
          <Button 
            variant="ghost" 
            size="sm"
            onClick={() => navigate("/")}
            className="text-white hover:bg-white/20 p-1 md:p-2"
          >
            <Home className="h-4 w-4" />
            <span className="hidden md:ml-2 md:inline">Home</span>
          </Button>
          <Button 
            variant="ghost" 
            size="sm"
            onClick={() => navigate("/users")}
            className="text-white hover:bg-white/20 p-1 md:p-2"
          >
            <User className="h-4 w-4" />
            <span className="hidden md:ml-2 md:inline">Users</span>
          </Button>
          <Button 
            variant="ghost" 
            size="sm"
            onClick={() => navigate("/dashboard")}
            className="text-white hover:bg-white/20 p-1 md:p-2"
          >
            <Activity className="h-4 w-4" />
            <span className="hidden md:ml-2 md:inline">Dashboard</span>
          </Button>
        </div>
      </div>
    </nav>
  );
};