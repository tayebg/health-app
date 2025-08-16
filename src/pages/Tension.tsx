import { HealthNavigation } from "@/components/HealthNavigation";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Activity, ChevronDown, ChevronUp } from "lucide-react";
import { useHealthData } from "@/contexts/HealthDataContext";
import { useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
} from "recharts";

const Tension = () => {
  const { healthData, getWeekData, getAllWeeks } = useHealthData();
  const [showChart, setShowChart] = useState(false);
  const [selectedWeek, setSelectedWeek] = useState<number | null>(null);
  const availableWeeks = getAllWeeks();
  const days = ["D1", "D2", "D3", "D4", "D5", "D6", "D7"];

  const getFilteredData = () => {
    if (selectedWeek === null) return healthData;
    return getWeekData(selectedWeek + 1);
  };

  return (
    <div className="min-h-screen health-gradient">
      <HealthNavigation showBackButton title="Tension" />
      
      <div className="container mx-auto p-6">
        {/* Header with icon */}
        <div className="flex items-center justify-start mb-6">
          <div className="bg-medical-accent rounded-lg p-3 mr-4">
            <Activity className="h-8 w-8 text-medical-accent-foreground" />
          </div>
        </div>

        {/* View More Section */}
        <Card className="health-card mb-6">
          <CardHeader>
            <div className="flex items-center justify-between">
              <CardTitle className="text-xl text-primary flex items-center gap-2">
                <Activity className="h-5 w-5" />
                Blood Pressure Tracking
              </CardTitle>
              <Button
                variant="ghost"
                onClick={() => setShowChart(!showChart)}
                className="flex items-center gap-2"
              >
                View More
                {showChart ? <ChevronUp className="h-4 w-4" /> : <ChevronDown className="h-4 w-4" />}
              </Button>
            </div>
          </CardHeader>
          {showChart && (
            <CardContent>
              {healthData.length > 0 ? (
                <div className="h-[300px] w-full">
                  <ResponsiveContainer width="100%" height="100%">
                    <LineChart
                      data={getFilteredData()}
                      margin={{
                        top: 20,
                        right: 30,
                        left: 50,
                        bottom: 60,
                      }}
                    >
                      <CartesianGrid strokeDasharray="3 3" className="opacity-30" />
                      <XAxis 
                        dataKey="day" 
                        fontSize={12}
                        tick={{ fill: 'hsl(var(--foreground))' }}
                        label={{ value: 'Days (Week)', position: 'insideBottom', offset: -10, style: { textAnchor: 'middle', fill: 'hsl(var(--foreground))' } }}
                      />
                      <YAxis 
                        fontSize={12}
                        tick={{ fill: 'hsl(var(--foreground))' }}
                        label={{ value: 'Blood Pressure (mmHg)', angle: -90, position: 'insideLeft', style: { textAnchor: 'middle', fill: 'hsl(var(--foreground))' } }}
                      />
                      <Tooltip 
                        contentStyle={{
                          backgroundColor: 'hsl(var(--background))',
                          border: '1px solid hsl(var(--border))',
                          borderRadius: '8px',
                          color: 'hsl(var(--foreground))'
                        }}
                        formatter={(value) => [`${value} mmHg`, 'Blood Pressure']}
                        labelFormatter={(label) => `Day ${label}`}
                      />
                      <Line
                        type="monotone"
                        dataKey="tension"
                        stroke="hsl(var(--health-warning))"
                        strokeWidth={3}
                        dot={{ fill: "hsl(var(--health-warning))", strokeWidth: 2, r: 4 }}
                        activeDot={{ r: 6, stroke: "hsl(var(--health-warning))", strokeWidth: 2 }}
                      />
                    </LineChart>
                  </ResponsiveContainer>
                </div>
              ) : (
                <div className="h-[300px] flex items-center justify-center text-muted-foreground">
                  No blood pressure data available
                </div>
              )}
            </CardContent>
          )}
        </Card>

        {/* Week indicators */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          {availableWeeks.map((weekNum, index) => (
            <Card 
              key={weekNum} 
              className={`health-card cursor-pointer transition-all ${
                selectedWeek === index ? 'ring-2 ring-primary' : ''
              }`}
              onClick={() => setSelectedWeek(selectedWeek === index ? null : index)}
            >
              <CardHeader className="pb-4">
                <div className="text-center">
                  <div className="flex justify-center space-x-1 mb-2">
                    {days.map((day) => (
                      <div 
                        key={day} 
                        className="w-6 h-6 bg-primary/20 rounded text-xs flex items-center justify-center text-primary font-medium"
                      >
                        {day.slice(1)}
                      </div>
                    ))}
                  </div>
                  <h3 className="font-semibold text-primary">Week {weekNum}</h3>
                  {selectedWeek === index && (
                    <p className="text-xs text-muted-foreground mt-1">Selected</p>
                  )}
                </div>
              </CardHeader>
            </Card>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Tension;