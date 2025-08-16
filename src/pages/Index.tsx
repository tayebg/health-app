import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { ArrowUp, Heart, Activity, Shield } from "lucide-react";
import medicalHeroBg from "@/assets/medical-hero-bg.jpg";

const Index = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen relative overflow-hidden">
      {/* Background with medical hero image */}
      <div 
        className="absolute inset-0 bg-cover bg-center"
        style={{ 
          backgroundImage: `url(${medicalHeroBg})` 
        }}
      >
        <div className="absolute inset-0 health-gradient-dark opacity-90"></div>
      </div>

      {/* Content */}
      <div className="relative z-10 min-h-screen flex flex-col items-center justify-center px-6">
        <div className="text-center max-w-4xl mx-auto">
          {/* Main title */}
          <h1 className="text-5xl md:text-7xl font-bold text-white mb-4">
            E-Health
          </h1>
          
          {/* Subtitle */}
          <p className="text-xl md:text-2xl text-white/90 mb-12 font-light">
            Your Personal Health Companion
          </p>

          {/* Features grid */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12 max-w-2xl mx-auto">
            <div className="flex flex-col items-center text-white/90">
              <Heart className="h-12 w-12 mb-3 text-medical-accent" />
              <h3 className="font-semibold mb-2">Health Tracking</h3>
              <p className="text-sm text-white/70">Monitor your vital signs and health metrics</p>
            </div>
            
            <div className="flex flex-col items-center text-white/90">
              <Activity className="h-12 w-12 mb-3 text-medical-accent" />
              <h3 className="font-semibold mb-2">Data Analysis</h3>
              <p className="text-sm text-white/70">Get insights from your health data trends</p>
            </div>
            
            <div className="flex flex-col items-center text-white/90">
              <Shield className="h-12 w-12 mb-3 text-medical-accent" />
              <h3 className="font-semibold mb-2">Secure & Private</h3>
              <p className="text-sm text-white/70">Your health data is protected and confidential</p>
            </div>
          </div>

          {/* CTA Button */}
          <div className="animate-bounce">
            <Button 
              variant="medical" 
              size="lg"
              onClick={() => navigate("/data-entry")}
              className="text-lg px-8 py-4 min-w-64"
            >
              <ArrowUp className="h-5 w-5 mr-2" />
              Start Health Tracking
            </Button>
          </div>

          {/* Quick navigation */}
          <div className="mt-8 flex flex-wrap justify-center gap-4">
            <Button 
              variant="ghost" 
              onClick={() => navigate("/users")}
              className="text-white/80 hover:text-white hover:bg-white/20"
            >
              View Users
            </Button>
            <Button 
              variant="ghost" 
              onClick={() => navigate("/dashboard")}
              className="text-white/80 hover:text-white hover:bg-white/20"
            >
              Health Dashboard
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Index;
