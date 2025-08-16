import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { supabase } from '@/integrations/supabase/client';

interface HealthDataEntry {
  id: string;
  weight: number;
  temperature: number;
  tension: string;
  week: number;
  day: number;
  date: string;
}

interface HealthDataContextType {
  healthData: HealthDataEntry[];
  addHealthData: (data: Omit<HealthDataEntry, 'id' | 'date'>) => Promise<void>;
  getWeekData: (week: number) => HealthDataEntry[];
  getAllWeeks: () => number[];
  loading: boolean;
}

const HealthDataContext = createContext<HealthDataContextType | undefined>(undefined);

export const useHealthData = () => {
  const context = useContext(HealthDataContext);
  if (!context) {
    throw new Error('useHealthData must be used within a HealthDataProvider');
  }
  return context;
};

export const HealthDataProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [healthData, setHealthData] = useState<HealthDataEntry[]>([]);
  const [loading, setLoading] = useState(true);

  // Fetch health data from Supabase on component mount
  useEffect(() => {
    fetchHealthData();
  }, []);

  const fetchHealthData = async () => {
    try {
      setLoading(true);
      const { data, error } = await supabase
        .from('health_data')
        .select('*')
        .order('created_at', { ascending: true });

      if (error) {
        console.error('Error fetching health data:', error);
        return;
      }

      const formattedData: HealthDataEntry[] = data.map(item => ({
        id: item.id,
        weight: item.weight,
        temperature: item.temperature,
        tension: item.tension,
        week: item.week,
        day: item.day,
        date: item.date
      }));

      setHealthData(formattedData);
    } catch (error) {
      console.error('Error fetching health data:', error);
    } finally {
      setLoading(false);
    }
  };

  const addHealthData = async (data: Omit<HealthDataEntry, 'id' | 'date'>) => {
    try {
      const { data: newEntry, error } = await supabase
        .from('health_data')
        .insert([{
          weight: data.weight,
          temperature: data.temperature,
          tension: data.tension,
          week: data.week,
          day: data.day
        }])
        .select()
        .single();

      if (error) {
        console.error('Error adding health data:', error);
        throw error;
      }

      const formattedEntry: HealthDataEntry = {
        id: newEntry.id,
        weight: newEntry.weight,
        temperature: newEntry.temperature,
        tension: newEntry.tension,
        week: newEntry.week,
        day: newEntry.day,
        date: newEntry.date
      };

      setHealthData(prev => [...prev, formattedEntry]);
    } catch (error) {
      console.error('Error adding health data:', error);
      throw error;
    }
  };

  const getWeekData = (week: number) => {
    return healthData.filter(entry => entry.week === week);
  };

  const getAllWeeks = () => {
    const weeks = [...new Set(healthData.map(entry => entry.week))];
    return weeks.sort((a, b) => a - b);
  };

  return (
    <HealthDataContext.Provider value={{ healthData, addHealthData, getWeekData, getAllWeeks, loading }}>
      {children}
    </HealthDataContext.Provider>
  );
};