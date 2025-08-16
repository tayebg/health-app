import { HealthNavigation } from "@/components/HealthNavigation";
import { HealthCard } from "@/components/HealthCard";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Thermometer, Activity, Weight, ArrowLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useHealthData } from "@/contexts/HealthDataContext";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
} from "recharts";

const Dashboard = () => {
  const navigate = useNavigate();
  const { healthData, getAllWeeks, loading } = useHealthData();

  // Calculate data statistics
  const totalEntries = healthData.length;
  const availableWeeks = getAllWeeks();
  const totalWeeks = availableWeeks.length;
  const uniqueDays = [...new Set(healthData.map(entry => `${entry.week}-${entry.day}`))].length;
  
  // Get latest entry
  const latestEntry = healthData.length > 0 ? healthData[healthData.length - 1] : null;

  const healthMetrics = [
    {
      title: "Temperature",
      icon: <Thermometer className="h-6 w-6 text-health-danger" />,
      onClick: () => navigate("/temperature"),
      count: healthData.filter(entry => entry.temperature).length,
    },
    {
      title: "Tension", 
      icon: <Activity className="h-6 w-6 text-health-warning" />,
      onClick: () => navigate("/tension"),
      count: healthData.filter(entry => entry.tension).length,
    },
    {
      title: "Weight",
      icon: <Weight className="h-6 w-6 text-health-good" />,
      onClick: () => navigate("/weight"),
      count: healthData.filter(entry => entry.weight).length,
    }
  ];

  return (
    <div className="min-h-screen health-gradient">
      <HealthNavigation showBackButton title="Health Dashboard" />
      
      <div className="container mx-auto p-6 max-w-4xl">
        {loading ? (
          <div className="flex items-center justify-center py-12">
            <div className="text-primary text-lg">Loading health data...</div>
          </div>
        ) : (
          <>
            {/* Data Summary Section */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
              <Card className="health-card text-center">
                <CardContent className="pt-6">
                  <div className="text-2xl font-bold text-primary">{totalEntries}</div>
                  <p className="text-sm text-muted-foreground">Total Entries</p>
                </CardContent>
              </Card>
              <Card className="health-card text-center">
                <CardContent className="pt-6">
                  <div className="text-2xl font-bold text-primary">{totalWeeks}</div>
                  <p className="text-sm text-muted-foreground">Weeks Recorded</p>
                </CardContent>
              </Card>
              <Card className="health-card text-center">
                <CardContent className="pt-6">
                  <div className="text-2xl font-bold text-primary">{uniqueDays}</div>
                  <p className="text-sm text-muted-foreground">Days Recorded</p>
                </CardContent>
              </Card>
              <Card className="health-card text-center">
                <CardContent className="pt-6">
                  <div className="text-2xl font-bold text-primary">
                    {latestEntry ? `W${latestEntry.week}D${latestEntry.day}` : '--'}
                  </div>
                  <p className="text-sm text-muted-foreground">Latest Entry</p>
                </CardContent>
              </Card>
            </div>

            {/* Health Metrics Section */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
              {healthMetrics.map((metric) => (
                <HealthCard
                  key={metric.title}
                  title={metric.title}
                  icon={metric.icon}
                  onClick={metric.onClick}
                  className="text-center"
                >
                  <div className="mb-2">
                    <span className="text-2xl font-bold text-primary">{metric.count}</span>
                    <span className="text-sm text-muted-foreground ml-1">entries</span>
                  </div>
                  <p className="text-muted-foreground mb-4">
                    View weekly overview and track your {metric.title.toLowerCase()} data
                  </p>
                  <Button variant="health" className="w-full">
                    View Details
                  </Button>
                </HealthCard>
              ))}
            </div>

            {/* Week Summary */}
            {totalWeeks > 0 && (
              <Card className="health-card">
                <CardHeader>
                  <CardTitle className="text-primary">Week Summary</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="flex flex-wrap gap-2">
                    {availableWeeks.map((week) => (
                      <div key={week} className="bg-primary/10 text-primary px-3 py-1 rounded-full text-sm">
                        Week {week} ({healthData.filter(entry => entry.week === week).length} entries)
                      </div>
                    ))}
                  </div>
                </CardContent>
              </Card>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default Dashboard;