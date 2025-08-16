-- Create health_data table for storing health measurements
CREATE TABLE public.health_data (
  id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  weight NUMERIC(5,2) NOT NULL,
  temperature NUMERIC(4,2) NOT NULL,
  tension TEXT NOT NULL,
  week INTEGER NOT NULL CHECK (week >= 1 AND week <= 52),
  day INTEGER NOT NULL CHECK (day >= 1 AND day <= 7),
  date DATE NOT NULL DEFAULT CURRENT_DATE,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

-- Enable Row Level Security
ALTER TABLE public.health_data ENABLE ROW LEVEL SECURITY;

-- Create policy to allow all operations for now (will need user-specific policies when auth is added)
CREATE POLICY "Allow all operations on health_data" 
ON public.health_data 
FOR ALL 
USING (true) 
WITH CHECK (true);

-- Create trigger for automatic timestamp updates
CREATE TRIGGER update_health_data_updated_at
  BEFORE UPDATE ON public.health_data
  FOR EACH ROW
  EXECUTE FUNCTION public.update_updated_at_column();

-- Add some sample data to match existing context data
INSERT INTO public.health_data (weight, temperature, tension, week, day, date) VALUES
  (70.5, 36.5, '120/80', 1, 1, '2024-01-01'),
  (70.2, 36.8, '118/78', 1, 3, '2024-01-03'),
  (69.8, 36.6, '122/82', 1, 5, '2024-01-05');