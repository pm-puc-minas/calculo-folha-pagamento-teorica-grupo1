import { useEffect } from 'react';

interface AlertProps {
  message: string;
  type: 'success' | 'error';
  onClose: () => void;
}

function Alert({ message, type, onClose }: AlertProps) {
  useEffect(() => {
    const timer = setTimeout(() => onClose(), 5000);
    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <div className={`alert alert-${type}`} style={{ display: 'block' }}>
      {message}
      <button
        onClick={onClose}
        style={{ float: 'right', background: 'none', border: 'none', cursor: 'pointer', fontSize: '1.2rem' }}
      >
        ×
      </button>
    </div>
  );
}

export default Alert;

