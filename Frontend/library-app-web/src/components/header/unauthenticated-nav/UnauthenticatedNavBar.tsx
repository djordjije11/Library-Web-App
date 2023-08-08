import { Navbar, Typography } from "@material-tailwind/react";

export default function UnauthenticatedNavBar() {
  return (
    <Navbar className="bg-gray-50 sticky top-0 z-10 h-max max-w-full rounded-none py-2 px-4 lg:px-8 lg:py-4">
      <div className="flex items-center justify-between text-blue-gray-900">
        <Typography as="a" className="mr-4 cursor-pointer py-1.5 font-medium">
          <span>Library</span>
        </Typography>
      </div>
    </Navbar>
  );
}
