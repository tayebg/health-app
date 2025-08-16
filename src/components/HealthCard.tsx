import { ReactNode } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { cn } from "@/lib/utils";

interface HealthCardProps {
  title: string;
  children: ReactNode;
  className?: string;
  icon?: ReactNode;
  onClick?: () => void;
}

export const HealthCard = ({ title, children, className, icon, onClick }: HealthCardProps) => {
  return (
    <Card 
      className={cn(
        "health-card health-transition cursor-pointer hover:scale-105 hover:shadow-xl", 
        className
      )}
      onClick={onClick}
    >
      <CardHeader className="flex flex-row items-center space-y-0 pb-2">
        {icon && (
          <div className="mr-3 p-2 rounded-lg bg-medical-accent/10">
            {icon}
          </div>
        )}
        <CardTitle className="text-lg font-semibold text-primary">{title}</CardTitle>
      </CardHeader>
      <CardContent>
        {children}
      </CardContent>
    </Card>
  );
};