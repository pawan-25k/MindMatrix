import React, { useState } from 'react';
import { 
  Search, 
  Plus, 
  MapPin, 
  Calendar, 
  User, 
  LogOut, 
  ChevronLeft, 
  Camera, 
  Tag, 
  Clock,
  Filter,
  Package,
  ShieldCheck,
  Mail
} from 'lucide-react';
import { motion, AnimatePresence } from 'motion/react';

// --- Types ---
type Role = 'Student' | 'Admin';
type ItemType = 'Lost' | 'Found';

interface Item {
  id: string;
  name: string;
  category: string;
  location: string;
  date: string;
  description: string;
  type: ItemType;
  status: 'Pending' | 'Resolved';
  reporter: string;
  contact: string;
}

type Screen = 'Login' | 'Home' | 'ReportLost' | 'ReportFound' | 'ItemList' | 'ItemDetail';

// --- Mock Data ---
const MOCK_ITEMS: Item[] = [
  {
    id: '1',
    name: 'Blue Water Bottle',
    category: 'Personal Items',
    location: 'Main Library - 2nd Floor',
    date: '2024-03-15',
    description: 'Hydroflask, blue color, has a sticker of a cat on it.',
    type: 'Lost',
    status: 'Pending',
    reporter: 'John Doe',
    contact: 'john.doe@university.edu'
  },
  {
    id: '2',
    name: 'Keys with Red Lanyard',
    category: 'Keys',
    location: 'Student Union Cafeteria',
    date: '2024-03-16',
    description: 'Set of 3 keys, one is a car key. Red lanyard says "Alumni".',
    type: 'Found',
    status: 'Pending',
    reporter: 'Jane Smith',
    contact: 'jane.smith@university.edu'
  },
  {
    id: '3',
    name: 'MacBook Air 13"',
    category: 'Electronics',
    location: 'Engineering Building - Room 302',
    date: '2024-03-14',
    description: 'Silver MacBook Air, has a clear plastic case.',
    type: 'Lost',
    status: 'Pending',
    reporter: 'Alice Wong',
    contact: 'alice.w@university.edu'
  }
];

// --- Components ---

const Button = ({ 
  children, 
  onClick, 
  variant = 'primary', 
  className = '',
  disabled = false,
  fullWidth = false
}: { 
  children: React.ReactNode, 
  onClick?: () => void, 
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost' | 'danger',
  className?: string,
  disabled?: boolean,
  fullWidth?: boolean
}) => {
  const variants = {
    primary: 'bg-indigo-600 text-white hover:bg-indigo-700 shadow-md',
    secondary: 'bg-emerald-600 text-white hover:bg-emerald-700 shadow-md',
    outline: 'border-2 border-indigo-600 text-indigo-600 hover:bg-indigo-50',
    ghost: 'text-gray-600 hover:bg-gray-100',
    danger: 'bg-rose-600 text-white hover:bg-rose-700 shadow-md'
  };

  return (
    <button 
      onClick={onClick}
      disabled={disabled}
      className={`px-6 py-3 rounded-2xl font-semibold transition-all active:scale-95 disabled:opacity-50 disabled:pointer-events-none flex items-center justify-center gap-2 ${fullWidth ? 'w-full' : ''} ${variants[variant]} ${className}`}
    >
      {children}
    </button>
  );
};

const Input = ({ 
  label, 
  type = 'text', 
  value, 
  onChange, 
  placeholder,
  icon: Icon
}: { 
  label: string, 
  type?: string, 
  value: string, 
  onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void,
  placeholder?: string,
  icon?: any
}) => (
  <div className="space-y-1.5 w-full text-left">
    <label className="text-sm font-medium text-gray-700 ml-1">{label}</label>
    <div className="relative">
      {Icon && <Icon className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 w-5 h-5" />}
      <input
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className={`w-full bg-gray-50 border-none ring-1 ring-gray-200 focus:ring-2 focus:ring-indigo-500 rounded-2xl py-3.5 ${Icon ? 'pl-12' : 'pl-5'} pr-5 outline-none transition-all`}
      />
    </div>
  </div>
);

// --- Main App ---

export default function App() {
  const [screen, setScreen] = useState<Screen>('Login');
  const [user, setUser] = useState<{ email: string, role: Role } | null>(null);
  const [items, setItems] = useState<Item[]>(MOCK_ITEMS);
  const [selectedItem, setSelectedItem] = useState<Item | null>(null);

  // Login State
  const [loginEmail, setLoginEmail] = useState('');
  const [loginPass, setLoginPass] = useState('');
  const [loginRole, setLoginRole] = useState<Role>('Student');

  // Report State
  const [reportName, setReportName] = useState('');
  const [reportCategory, setReportCategory] = useState('');
  const [reportLocation, setReportLocation] = useState('');
  const [reportDate, setReportDate] = useState('');
  const [reportDesc, setReportDesc] = useState('');

  const handleLogin = () => {
    if (loginEmail && loginPass) {
      setUser({ email: loginEmail, role: loginRole });
      setScreen('Home');
    }
  };

  const handleLogout = () => {
    setUser(null);
    setScreen('Login');
    setLoginEmail('');
    setLoginPass('');
  };

  const handleReport = (type: ItemType) => {
    const newItem: Item = {
      id: Math.random().toString(36).substr(2, 9),
      name: reportName || 'Unnamed Item',
      category: reportCategory || 'General',
      location: reportLocation || 'Campus',
      date: reportDate || new Date().toISOString().split('T')[0],
      description: reportDesc || 'No description provided.',
      type: type,
      status: 'Pending',
      reporter: user?.email || 'Anonymous',
      contact: user?.email || ''
    };
    setItems([newItem, ...items]);
    setScreen('Home');
    // Reset fields
    setReportName('');
    setReportCategory('');
    setReportLocation('');
    setReportDate('');
    setReportDesc('');
  };

  const navigateToDetail = (item: Item) => {
    setSelectedItem(item);
    setScreen('ItemDetail');
  };

  return (
    <div className="min-h-screen bg-slate-100 text-slate-900 font-sans flex justify-center items-center p-4">
      {/* Mobile Container Simulation */}
      <div className="w-full max-w-md bg-white h-[850px] shadow-2xl relative overflow-hidden flex flex-col rounded-[3rem] border-[8px] border-slate-900">
        
        <AnimatePresence mode="wait">
          {/* --- LOGIN SCREEN --- */}
          {screen === 'Login' && (
            <motion.div 
              key="login"
              initial={{ opacity: 0, scale: 0.95 }}
              animate={{ opacity: 1, scale: 1 }}
              exit={{ opacity: 0, scale: 1.05 }}
              className="flex-1 flex flex-col p-8 pt-20"
            >
              <div className="mb-12 text-center">
                <div className="w-20 h-20 bg-indigo-600 rounded-3xl flex items-center justify-center mx-auto mb-6 shadow-lg shadow-indigo-200">
                  <Package className="text-white w-10 h-10" />
                </div>
                <h1 className="text-3xl font-bold tracking-tight text-slate-900">Campus Portal</h1>
                <p className="text-slate-500 mt-2">Lost & Found Management</p>
              </div>

              <div className="space-y-6">
                <div className="flex bg-gray-100 p-1.5 rounded-2xl">
                  <button 
                    onClick={() => setLoginRole('Student')}
                    className={`flex-1 py-2.5 rounded-xl text-sm font-semibold transition-all ${loginRole === 'Student' ? 'bg-white shadow-sm text-indigo-600' : 'text-gray-500'}`}
                  >
                    Student
                  </button>
                  <button 
                    onClick={() => setLoginRole('Admin')}
                    className={`flex-1 py-2.5 rounded-xl text-sm font-semibold transition-all ${loginRole === 'Admin' ? 'bg-white shadow-sm text-indigo-600' : 'text-gray-500'}`}
                  >
                    Admin
                  </button>
                </div>

                <Input 
                  label="Email Address" 
                  icon={Mail}
                  placeholder="name@university.edu"
                  value={loginEmail}
                  onChange={(e) => setLoginEmail(e.target.value)}
                />
                
                <Input 
                  label="Password" 
                  type="password"
                  icon={ShieldCheck}
                  placeholder="••••••••"
                  value={loginPass}
                  onChange={(e) => setLoginPass(e.target.value)}
                />

                <Button fullWidth onClick={handleLogin} className="mt-4">
                  Sign In
                </Button>
                
                <p className="text-center text-sm text-slate-400 mt-8">
                  Forgot password? <span className="text-indigo-600 font-medium cursor-pointer">Reset here</span>
                </p>
              </div>
            </motion.div>
          )}

          {/* --- HOME SCREEN --- */}
          {screen === 'Home' && (
            <motion.div 
              key="home"
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              className="flex-1 flex flex-col overflow-y-auto"
            >
              <header className="p-6 pt-12 flex justify-between items-center">
                <div>
                  <h2 className="text-2xl font-bold">Hello, {user?.role}!</h2>
                  <p className="text-slate-500 text-sm">{user?.email}</p>
                </div>
                <button onClick={handleLogout} className="p-3 bg-slate-100 rounded-2xl text-slate-600 hover:bg-rose-50 hover:text-rose-600 transition-colors">
                  <LogOut className="w-5 h-5" />
                </button>
              </header>

              <main className="flex-1 p-6 space-y-6">
                <div className="grid grid-cols-2 gap-4">
                  <motion.button 
                    whileHover={{ scale: 1.02 }}
                    whileTap={{ scale: 0.98 }}
                    onClick={() => setScreen('ReportLost')}
                    className="aspect-square bg-indigo-50 rounded-3xl p-6 flex flex-col justify-between border border-indigo-100 group text-left"
                  >
                    <div className="w-12 h-12 bg-indigo-600 rounded-2xl flex items-center justify-center text-white shadow-md group-hover:shadow-indigo-200 transition-all">
                      <Plus className="w-6 h-6" />
                    </div>
                    <div>
                      <h3 className="font-bold text-indigo-900">Report Lost</h3>
                      <p className="text-xs text-indigo-600/70 mt-1">I lost something</p>
                    </div>
                  </motion.button>

                  <motion.button 
                    whileHover={{ scale: 1.02 }}
                    whileTap={{ scale: 0.98 }}
                    onClick={() => setScreen('ReportFound')}
                    className="aspect-square bg-emerald-50 rounded-3xl p-6 flex flex-col justify-between border border-emerald-100 group text-left"
                  >
                    <div className="w-12 h-12 bg-emerald-600 rounded-2xl flex items-center justify-center text-white shadow-md group-hover:shadow-emerald-200 transition-all">
                      <Search className="w-6 h-6" />
                    </div>
                    <div>
                      <h3 className="font-bold text-emerald-900">Report Found</h3>
                      <p className="text-xs text-emerald-600/70 mt-1">I found something</p>
                    </div>
                  </motion.button>
                </div>

                <div className="bg-slate-900 rounded-3xl p-6 text-white relative overflow-hidden">
                  <div className="relative z-10">
                    <h3 className="text-xl font-bold mb-2">Recent Activity</h3>
                    <p className="text-slate-400 text-sm mb-4">Check the latest items reported on campus.</p>
                    <button 
                      className="bg-white/10 text-white hover:bg-white/20 px-4 py-2 rounded-xl text-sm font-medium transition-colors"
                      onClick={() => setScreen('ItemList')}
                    >
                      View All Items
                    </button>
                  </div>
                  <Package className="absolute -right-4 -bottom-4 w-32 h-32 text-white/5 rotate-12" />
                </div>

                <section>
                  <div className="flex justify-between items-center mb-4">
                    <h3 className="font-bold text-lg">Quick Access</h3>
                  </div>
                  <div className="space-y-3">
                    {['My Reports', 'Notifications', 'Campus Map'].map((item) => (
                      <div key={item} className="flex items-center justify-between p-4 bg-white rounded-2xl border border-slate-100 hover:border-indigo-200 transition-all cursor-pointer group">
                        <span className="font-medium text-slate-700">{item}</span>
                        <ChevronLeft className="w-5 h-5 text-slate-300 rotate-180 group-hover:text-indigo-500 transition-all" />
                      </div>
                    ))}
                  </div>
                </section>
              </main>
            </motion.div>
          )}

          {/* --- REPORT SCREENS --- */}
          {(screen === 'ReportLost' || screen === 'ReportFound') && (
            <motion.div 
              key="report"
              initial={{ x: 400 }}
              animate={{ x: 0 }}
              exit={{ x: 400 }}
              className="flex-1 flex flex-col bg-white"
            >
              <header className="p-6 pt-12 flex items-center gap-4">
                <button onClick={() => setScreen('Home')} className="p-2 bg-slate-100 rounded-xl">
                  <ChevronLeft className="w-6 h-6" />
                </button>
                <h2 className="text-2xl font-bold">Report {screen === 'ReportLost' ? 'Lost' : 'Found'}</h2>
              </header>

              <main className="flex-1 p-6 overflow-y-auto space-y-6 pb-24">
                <div className="w-full aspect-video bg-slate-100 rounded-3xl flex flex-col items-center justify-center border-2 border-dashed border-slate-200 text-slate-400 hover:bg-slate-50 transition-all cursor-pointer">
                  <Camera className="w-10 h-10 mb-2" />
                  <span className="text-sm font-medium">Add Photos</span>
                </div>

                <Input 
                  label="Item Name" 
                  placeholder="e.g. Blue Water Bottle"
                  value={reportName}
                  onChange={(e) => setReportName(e.target.value)}
                />

                <div className="grid grid-cols-2 gap-4">
                  <Input 
                    label="Category" 
                    placeholder="e.g. Electronics"
                    value={reportCategory}
                    onChange={(e) => setReportCategory(e.target.value)}
                  />
                  <Input 
                    label="Date" 
                    type="date"
                    value={reportDate}
                    onChange={(e) => setReportDate(e.target.value)}
                  />
                </div>

                <Input 
                  label="Location" 
                  icon={MapPin}
                  placeholder="Where was it seen?"
                  value={reportLocation}
                  onChange={(e) => setReportLocation(e.target.value)}
                />

                <div className="space-y-1.5 text-left">
                  <label className="text-sm font-medium text-gray-700 ml-1">Description</label>
                  <textarea 
                    rows={4}
                    placeholder="Provide more details..."
                    value={reportDesc}
                    onChange={(e) => setReportDesc(e.target.value)}
                    className="w-full bg-gray-50 border-none ring-1 ring-gray-200 focus:ring-2 focus:ring-indigo-500 rounded-2xl py-3.5 px-5 outline-none transition-all resize-none"
                  />
                </div>
              </main>

              <div className="absolute bottom-0 left-0 right-0 p-6 bg-white border-t border-slate-100">
                <Button 
                  fullWidth 
                  variant={screen === 'ReportLost' ? 'primary' : 'secondary'}
                  onClick={() => handleReport(screen === 'ReportLost' ? 'Lost' : 'Found')}
                >
                  Submit Report
                </Button>
              </div>
            </motion.div>
          )}

          {/* --- ITEM LIST SCREEN --- */}
          {screen === 'ItemList' && (
            <motion.div 
              key="list"
              initial={{ x: 400 }}
              animate={{ x: 0 }}
              exit={{ x: 400 }}
              className="flex-1 flex flex-col bg-white"
            >
              <header className="p-6 pt-12 flex items-center justify-between">
                <div className="flex items-center gap-4">
                  <button onClick={() => setScreen('Home')} className="p-2 bg-slate-100 rounded-xl">
                    <ChevronLeft className="w-6 h-6" />
                  </button>
                  <h2 className="text-2xl font-bold">All Items</h2>
                </div>
                <button className="p-2 bg-slate-100 rounded-xl text-slate-600">
                  <Filter className="w-5 h-5" />
                </button>
              </header>

              <div className="px-6 mb-4">
                <div className="relative">
                  <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 w-5 h-5" />
                  <input 
                    type="text" 
                    placeholder="Search items..."
                    className="w-full bg-slate-100 rounded-2xl py-3 pl-12 pr-4 outline-none focus:ring-2 focus:ring-indigo-500 transition-all"
                  />
                </div>
              </div>

              <main className="flex-1 overflow-y-auto px-6 pb-6 space-y-4">
                {items.map((item) => (
                  <motion.div 
                    key={item.id}
                    whileTap={{ scale: 0.98 }}
                    onClick={() => navigateToDetail(item)}
                    className="p-4 bg-white rounded-3xl border border-slate-100 shadow-sm hover:shadow-md transition-all cursor-pointer flex gap-4 text-left"
                  >
                    <div className={`w-20 h-20 rounded-2xl flex items-center justify-center flex-shrink-0 ${item.type === 'Lost' ? 'bg-rose-50 text-rose-500' : 'bg-emerald-50 text-emerald-500'}`}>
                      <Package className="w-8 h-8" />
                    </div>
                    <div className="flex-1 min-w-0">
                      <div className="flex justify-between items-start mb-1">
                        <h4 className="font-bold text-slate-900 truncate">{item.name}</h4>
                        <span className={`text-[10px] font-bold uppercase tracking-wider px-2 py-0.5 rounded-full ${item.type === 'Lost' ? 'bg-rose-100 text-rose-600' : 'bg-emerald-100 text-emerald-600'}`}>
                          {item.type}
                        </span>
                      </div>
                      <p className="text-xs text-slate-500 mb-2 truncate">{item.category}</p>
                      <div className="flex items-center gap-3 text-[11px] text-slate-400">
                        <div className="flex items-center gap-1">
                          <MapPin className="w-3 h-3" />
                          <span className="truncate max-w-[80px]">{item.location}</span>
                        </div>
                        <div className="flex items-center gap-1">
                          <Calendar className="w-3 h-3" />
                          <span>{item.date}</span>
                        </div>
                      </div>
                    </div>
                  </motion.div>
                ))}
              </main>
            </motion.div>
          )}

          {/* --- ITEM DETAIL SCREEN --- */}
          {screen === 'ItemDetail' && selectedItem && (
            <motion.div 
              key="detail"
              initial={{ y: 500 }}
              animate={{ y: 0 }}
              exit={{ y: 500 }}
              className="flex-1 flex flex-col bg-white"
            >
              <div className="relative h-72 bg-slate-200">
                <div className="absolute inset-0 flex items-center justify-center text-slate-400">
                  <Package className="w-20 h-20 opacity-20" />
                </div>
                <div className="absolute top-12 left-6 right-6 flex justify-between">
                  <button onClick={() => setScreen('ItemList')} className="p-2 bg-white/80 backdrop-blur-md rounded-xl shadow-lg">
                    <ChevronLeft className="w-6 h-6" />
                  </button>
                  <button className="p-2 bg-white/80 backdrop-blur-md rounded-xl shadow-lg">
                    <Tag className="w-6 h-6 text-indigo-600" />
                  </button>
                </div>
              </div>

              <main className="flex-1 -mt-8 bg-white rounded-t-[40px] p-8 space-y-6 shadow-2xl relative z-10 overflow-y-auto pb-24 text-left">
                <div className="flex justify-between items-center">
                  <div>
                    <span className={`text-xs font-bold uppercase tracking-widest px-3 py-1 rounded-full mb-3 inline-block ${selectedItem.type === 'Lost' ? 'bg-rose-100 text-rose-600' : 'bg-emerald-100 text-emerald-600'}`}>
                      {selectedItem.type}
                    </span>
                    <h2 className="text-3xl font-bold text-slate-900">{selectedItem.name}</h2>
                  </div>
                  <div className="text-right">
                    <div className="flex items-center gap-1 text-emerald-600 font-bold text-sm">
                      <Clock className="w-4 h-4" />
                      <span>{selectedItem.status}</span>
                    </div>
                  </div>
                </div>

                <div className="flex gap-4 overflow-x-auto pb-2">
                  <div className="flex items-center gap-2 px-4 py-2 bg-slate-50 rounded-2xl border border-slate-100 whitespace-nowrap">
                    <Tag className="w-4 h-4 text-indigo-500" />
                    <span className="text-sm font-medium text-slate-600">{selectedItem.category}</span>
                  </div>
                  <div className="flex items-center gap-2 px-4 py-2 bg-slate-50 rounded-2xl border border-slate-100 whitespace-nowrap">
                    <MapPin className="w-4 h-4 text-indigo-500" />
                    <span className="text-sm font-medium text-slate-600">{selectedItem.location}</span>
                  </div>
                </div>

                <section>
                  <h3 className="font-bold text-lg mb-2">Description</h3>
                  <p className="text-slate-600 leading-relaxed">
                    {selectedItem.description}
                  </p>
                </section>

                <section className="p-6 bg-indigo-50 rounded-3xl border border-indigo-100">
                  <h3 className="font-bold text-indigo-900 mb-4 flex items-center gap-2">
                    <User className="w-5 h-5" />
                    Reporter Info
                  </h3>
                  <div className="space-y-3">
                    <div className="flex justify-between text-sm">
                      <span className="text-indigo-600/70">Name</span>
                      <span className="font-bold text-indigo-900">{selectedItem.reporter}</span>
                    </div>
                    <div className="flex justify-between text-sm">
                      <span className="text-indigo-600/70">Contact</span>
                      <span className="font-bold text-indigo-900">{selectedItem.contact}</span>
                    </div>
                  </div>
                </section>
              </main>

              <div className="absolute bottom-0 left-0 right-0 p-6 bg-white border-t border-slate-100 flex gap-4">
                <Button fullWidth variant="outline" className="flex-1">
                  Share
                </Button>
                <Button fullWidth className="flex-[2]">
                  Contact Reporter
                </Button>
              </div>
            </motion.div>
          )}
        </AnimatePresence>

      </div>
    </div>
  );
}
