import { useLocation } from "react-router-dom";
import { useEffect } from "react";

const NotFound = () => {
  const location = useLocation();

  useEffect(() => {
    console.error(
      "404 Error: User attempted to access non-existent route:",
      location.pathname
    );
  }, [location.pathname]);

  return (
    <div className="min-h-screen health-gradient flex items-center justify-center">
      <div className="text-center">
        <div className="health-card max-w-md mx-auto p-8">
          <h1 className="text-4xl font-bold mb-4 text-primary">404</h1>
          <p className="text-xl text-muted-foreground mb-6">Oops! Page not found</p>
          <p className="text-sm text-muted-foreground mb-6">
            The health page you're looking for doesn't exist.
          </p>
          <a 
            href="/" 
            className="inline-flex items-center justify-center px-6 py-3 bg-medical-accent text-medical-accent-foreground rounded-lg hover:bg-medical-accent-hover health-transition font-semibold"
          >
            Return to E-Health Home
          </a>
        </div>
      </div>
    </div>
  );
};

export default NotFound;
