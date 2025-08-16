import { HealthNavigation } from "@/components/HealthNavigation";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useState } from "react";
import { useUsers } from "@/contexts/UserContext";
import { Edit, Trash2 } from "lucide-react";
import { toast } from "@/hooks/use-toast";

const Users = () => {
  const { users, updateUser, deleteUser } = useUsers();
  const [editingUser, setEditingUser] = useState<any>(null);
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);
  const [selectedUserId, setSelectedUserId] = useState<number | null>(null);
  const handleEdit = () => {
    const user = users.find(u => u.id === selectedUserId);
    if (user) {
      setEditingUser({ ...user });
      setIsEditDialogOpen(true);
    }
  };

  const handleSaveEdit = () => {
    if (editingUser) {
      updateUser(editingUser.id, {
        user: editingUser.user,
        fullName: editingUser.fullName,
        gender: editingUser.gender,
        email: editingUser.email
      });
      toast({
        title: "User Updated",
        description: "User information has been successfully updated",
      });
      setIsEditDialogOpen(false);
      setEditingUser(null);
    }
  };

  const handleDelete = () => {
    if (selectedUserId) {
      deleteUser(selectedUserId);
      toast({
        title: "User Deleted",
        description: "User has been successfully removed",
        variant: "destructive"
      });
      setSelectedUserId(null);
    }
  };

  return (
    <div className="min-h-screen health-gradient">
      <HealthNavigation showBackButton title="User Table" />
      
      <div className="container mx-auto p-6">
        <Card className="health-card">
          <CardHeader>
            <CardTitle className="text-2xl text-primary">User Table</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow className="bg-medical-accent/20">
                    <TableHead className="font-bold text-medical-accent-foreground">ID</TableHead>
                    <TableHead className="font-bold text-medical-accent-foreground">User</TableHead>
                    <TableHead className="font-bold text-medical-accent-foreground">Full Name</TableHead>
                    <TableHead className="font-bold text-medical-accent-foreground">Gender</TableHead>
                    <TableHead className="font-bold text-medical-accent-foreground">Email</TableHead>
                    <TableHead className="font-bold text-medical-accent-foreground">
                      <div className="flex items-center gap-2">
                        <Button
                          variant="ghost"
                          size="icon"
                          onClick={handleEdit}
                          disabled={!selectedUserId}
                          className="h-8 w-8 text-primary hover:text-primary-hover disabled:opacity-50"
                        >
                          <Edit className="h-4 w-4" />
                        </Button>
                        <Button
                          variant="ghost"
                          size="icon"
                          onClick={handleDelete}
                          disabled={!selectedUserId}
                          className="h-8 w-8 text-destructive hover:text-destructive/90 disabled:opacity-50"
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      </div>
                    </TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {users.map((user) => (
                    <TableRow 
                      key={user.id} 
                      className={`hover:bg-muted/50 health-transition cursor-pointer ${
                        selectedUserId === user.id ? 'bg-medical-accent/20' : ''
                      }`}
                      onClick={() => setSelectedUserId(user.id)}
                    >
                      <TableCell className="font-medium text-primary">{user.id}</TableCell>
                      <TableCell className="text-foreground">{user.user}</TableCell>
                      <TableCell className="text-foreground">{user.fullName}</TableCell>
                      <TableCell className="text-foreground">{user.gender}</TableCell>
                      <TableCell className="text-foreground">{user.email}</TableCell>
                      <TableCell></TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          </CardContent>
        </Card>

        {/* Edit User Dialog */}
        <Dialog open={isEditDialogOpen} onOpenChange={setIsEditDialogOpen}>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Edit User</DialogTitle>
            </DialogHeader>
            {editingUser && (
              <div className="space-y-4">
                <div>
                  <Label htmlFor="edit-user">Username</Label>
                  <Input
                    id="edit-user"
                    value={editingUser.user}
                    onChange={(e) => setEditingUser({...editingUser, user: e.target.value})}
                  />
                </div>
                <div>
                  <Label htmlFor="edit-fullname">Full Name</Label>
                  <Input
                    id="edit-fullname"
                    value={editingUser.fullName}
                    onChange={(e) => setEditingUser({...editingUser, fullName: e.target.value})}
                  />
                </div>
                <div>
                  <Label htmlFor="edit-gender">Gender</Label>
                  <Input
                    id="edit-gender"
                    value={editingUser.gender}
                    onChange={(e) => setEditingUser({...editingUser, gender: e.target.value})}
                  />
                </div>
                <div>
                  <Label htmlFor="edit-email">Email</Label>
                  <Input
                    id="edit-email"
                    value={editingUser.email}
                    onChange={(e) => setEditingUser({...editingUser, email: e.target.value})}
                  />
                </div>
                <div className="flex justify-end gap-2">
                  <Button variant="outline" onClick={() => setIsEditDialogOpen(false)}>
                    Cancel
                  </Button>
                  <Button onClick={handleSaveEdit}>
                    Save Changes
                  </Button>
                </div>
              </div>
            )}
          </DialogContent>
        </Dialog>
      </div>
    </div>
  );
};

export default Users;