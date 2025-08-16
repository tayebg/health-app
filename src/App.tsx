import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { HealthDataProvider } from "@/contexts/HealthDataContext";
import { UserProvider } from "@/contexts/UserContext";
import Index from "./pages/Index";
import NotFound from "./pages/NotFound";
import DataEntry from "./pages/DataEntry";
import Dashboard from "./pages/Dashboard";
import Users from "./pages/Users";
import Temperature from "./pages/Temperature";
import Tension from "./pages/Tension";
import WeightPage from "./pages/Weight";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <HealthDataProvider>
      <UserProvider>
        <TooltipProvider>
          <Toaster />
          <Sonner />
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Index />} />
              <Route path="/data-entry" element={<DataEntry />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/users" element={<Users />} />
              <Route path="/temperature" element={<Temperature />} />
              <Route path="/tension" element={<Tension />} />
              <Route path="/weight" element={<WeightPage />} />
              {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
              <Route path="*" element={<NotFound />} />
            </Routes>
          </BrowserRouter>
        </TooltipProvider>
      </UserProvider>
    </HealthDataProvider>
  </QueryClientProvider>
);

export default App;
