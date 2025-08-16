import { HealthNavigation } from "@/components/HealthNavigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "@/hooks/use-toast";
import { useHealthData } from "@/contexts/HealthDataContext";

const DataEntry = () => {
  const navigate = useNavigate();
  const { addHealthData } = useHealthData();
  const [formData, setFormData] = useState({
    weight: "",
    temperature: "", 
    tension: "",
    week: "",
    day: ""
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    // Validate form
    if (!formData.weight || !formData.temperature || !formData.tension || !formData.week || !formData.day) {
      toast({
        title: "Validation Error",
        description: "Please fill in all fields",
        variant: "destructive"
      });
      return;
    }

    // Validate ranges
    const weight = parseFloat(formData.weight);
    const temperature = parseFloat(formData.temperature);
    
    if (weight < 10 || weight > 300) {
      toast({
        title: "Invalid Weight",
        description: "Weight must be between 10kg and 300kg",
        variant: "destructive"
      });
      return;
    }

    if (temperature < 30 || temperature > 45) {
      toast({
        title: "Invalid Temperature", 
        description: "Temperature must be between 30°C and 45°C",
        variant: "destructive"
      });
      return;
    }

    // Validate tension format (e.g., 120/80)
    const tensionPattern = /^\d{2,3}\/\d{2,3}$/;
    if (!tensionPattern.test(formData.tension)) {
      toast({
        title: "Invalid Blood Pressure",
        description: "Please enter blood pressure in format: 120/80",
        variant: "destructive"
      });
      return;
    }

    try {
      // Add data to Supabase
      await addHealthData({
        weight: weight,
        temperature: temperature,
        tension: formData.tension,
        week: parseInt(formData.week),
        day: parseInt(formData.day)
      });

      toast({
        title: "Health Data Saved",
        description: "Your health data has been successfully recorded",
      });
      
      // Reset form and navigate to dashboard
      setFormData({
        weight: "",
        temperature: "", 
        tension: "",
        week: "",
        day: ""
      });

      // Auto-navigate to dashboard
      setTimeout(() => {
        navigate("/dashboard");
      }, 1500);
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to save health data. Please try again.",
        variant: "destructive"
      });
    }
  };

  const handleInputChange = (field: string, value: string) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  return (
    <div className="min-h-screen health-gradient">
      <HealthNavigation showBackButton title="Health Data Entry" />
      
      <div className="container mx-auto p-6 max-w-lg">
        <Card className="health-card">
          <CardHeader>
            <CardTitle className="text-2xl text-center text-primary">
              Health Data Entry
            </CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="space-y-2">
                <Label htmlFor="weight" className="text-primary font-medium">
                  Weight (kg):
                </Label>
                <Input
                  id="weight"
                  type="number"
                  step="0.1"
                  min="10"
                  max="300"
                  placeholder="Enter weight in kg (10-300)"
                  value={formData.weight}
                  onChange={(e) => handleInputChange("weight", e.target.value)}
                  className="health-form-input"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="temperature" className="text-primary font-medium">
                  Temperature (°C):
                </Label>
                <Input
                  id="temperature"
                  type="number"
                  step="0.1"
                  min="30"
                  max="45"
                  placeholder="Enter temperature in °C (30-45)"
                  value={formData.temperature}
                  onChange={(e) => handleInputChange("temperature", e.target.value)}
                  className="health-form-input"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="tension" className="text-primary font-medium">
                  Tension (mmHg):
                </Label>
                <Input
                  id="tension"
                  type="text"
                  placeholder="Enter blood pressure (e.g., 120/80)"
                  value={formData.tension}
                  onChange={(e) => handleInputChange("tension", e.target.value)}
                  className="health-form-input"
                  pattern="\d{2,3}/\d{2,3}"
                  title="Please enter blood pressure in format: 120/80"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="week" className="text-primary font-medium">
                  Week:
                </Label>
                <Input
                  id="week"
                  type="number"
                  min="1"
                  max="52"
                  placeholder="Enter week number (1-52)"
                  value={formData.week}
                  onChange={(e) => handleInputChange("week", e.target.value)}
                  className="health-form-input"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="day" className="text-primary font-medium">
                  Day:
                </Label>
                <Input
                  id="day"
                  type="number"
                  min="1"
                  max="7"
                  placeholder="Enter day (1-7)"
                  value={formData.day}
                  onChange={(e) => handleInputChange("day", e.target.value)}
                  className="health-form-input"
                />
              </div>

              <Button type="submit" variant="medical" className="w-full text-lg py-3">
                Submit
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default DataEntry;